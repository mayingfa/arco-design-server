package top.qiudb.injector;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.qiudb.common.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Optional;

/**
 * 用于处理@Comment注解，生成表字段注释
 */
@Component
public class CommentIntegrator implements Integrator {
    private static final Logger log = LoggerFactory.getLogger(CommentIntegrator.class);

    public static final CommentIntegrator INSTANCE = new CommentIntegrator();

    public CommentIntegrator() {
        super();
    }

    /**
     * Perform comment integration.
     *
     * @param metadata        The "compiled" representation of the mapping information
     * @param sessionFactory  The session factory being created
     * @param serviceRegistry The session factory's service registry
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
                          SessionFactoryServiceRegistry serviceRegistry) {
        processComment(metadata);
    }

    /**
     * Not used.
     * @param sessionFactoryImplementor The session factory being closed.
     * @param sessionFactoryServiceRegistry That session factory's service registry
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor,
                             SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        // Do nothing.
    }

    /**
     * Process comment annotation.
     *
     * @param metadata process annotation of this {@code Metadata}.
     */
    private void processComment(Metadata metadata) {
        for (PersistentClass persistentClass : metadata.getEntityBindings()) {
            // Process the Comment annotation is applied to Class
            Class<?> clazz = persistentClass.getMappedClass();
            log.debug("Class: {}", clazz.getName());
            if (clazz.isAnnotationPresent(Comment.class)) {
                Comment comment = clazz.getAnnotation(Comment.class);
                persistentClass.getTable().setComment(comment.value());
            }

            // Process Comment annotations of identifier.
            Property identifierProperty = persistentClass.getIdentifierProperty();
            if (identifierProperty != null) {
                log.debug("process identifierProperty");
                fieldComment(persistentClass, identifierProperty.getName());
            } else {
                log.debug("process identifierMapper");
                org.hibernate.mapping.Component component = persistentClass.getIdentifierMapper();
                if (component != null) {
                    //noinspection unchecked
                    Iterator<Property> iterator = component.getPropertyIterator();
                    while (iterator.hasNext()) {
                        fieldComment(persistentClass, iterator.next().getName());
                    }
                }
            }
            // Process fields with Comment annotation.
            //noinspection unchecked
            Iterator<Property> iterator = persistentClass.getPropertyIterator();
            while (iterator.hasNext()) {
                log.debug("process other property");
                fieldComment(persistentClass, iterator.next().getName());
            }
        }
    }

    /**
     * Process @{code comment} annotation of field.
     *
     * @param persistentClass Hibernate {@code PersistentClass}
     * @param columnName      name of field
     */
    private void fieldComment(PersistentClass persistentClass, String columnName) {
        Optional<Field> o = getOptionalField(persistentClass, columnName);
        if (!o.isPresent()) {
            log.error("some error when create comment! NoSuchFieldException," +
                            " persistentClass: {}, columnName: {}",
                    persistentClass, columnName);
            return;
        }

        Field field = o.get();
        try {
            if (field.isAnnotationPresent(Comment.class)) {
                String columnNameFromAnnotation = getColumnNameFromAnnotation(field);
                String comment = field.getAnnotation(Comment.class).value().trim();
                setColumnComment(persistentClass, columnName, columnNameFromAnnotation, comment);
            }
        } catch (SecurityException e) {
            log.error("some error when create comment! persistentClass: {}, columnName: {}",
                    persistentClass,
                    columnName,
                    e);
        }
    }

    private void setColumnComment(PersistentClass persistentClass, String columnName,
                                  String columnNameFromAnnotation, String comment) {
        boolean completed = false;
        Iterator<org.hibernate.mapping.Column> columnIterator = persistentClass
                .getTable()
                .getColumnIterator();
        while (columnIterator.hasNext() && !completed) {
            org.hibernate.mapping.Column column = columnIterator.next();
            if (!columnNameFromAnnotation.isEmpty()) {
                if (columnNameFromAnnotation.equalsIgnoreCase(column.getName())
                   || columnNameFromAnnotation.equalsIgnoreCase("["+column.getName()+"]")) {

                    column.setComment(comment);
                    completed = true;
                    log.debug("columnName: {}, column.getName(): {}, comment: {}",
                            columnNameFromAnnotation,
                            column.getName(),
                            comment);
                }
            } else {
                if (columnName.equalsIgnoreCase(
                        column.getName().replace("_", ""))) {
                    column.setComment(comment);
                    completed = true;
                    log.debug("columnName: {}, column.getName(): {}, comment: {}",
                            columnName,
                            column.getName(),
                            comment);
                }
            }
        }
    }

    private String getColumnNameFromAnnotation(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String annotationName = field.getAnnotation(Column.class).name().trim();
            if (!annotationName.isEmpty()) {
                return annotationName;
            }
        } else if (field.isAnnotationPresent(JoinColumn.class)) {
            String annotationName = field.getAnnotation(JoinColumn.class).name().trim();
            if (!annotationName.isEmpty()) {
                return annotationName;
            }
        }

        return "";
    }

    private Optional<Field> getOptionalField(PersistentClass persistentClass, String columnName) {
        Class<?> clazz = persistentClass.getMappedClass();
        Field field = FieldUtils.getDeclaredField(clazz, columnName);
        if (field != null) {
            return Optional.of(field);
        }

        // Search again include superclass fields
        return FieldUtils.getAllFieldsList(clazz)
                .stream().filter(e -> e.getName().equals(columnName))
                .findFirst();
    }
}
