package top.qiudb.module.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qiudb.common.domain.CommonResult;
import top.qiudb.module.user.domain.entity.User;
import top.qiudb.module.user.service.UserService;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "用户接口")
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public CommonResult<String> addUser(@RequestBody @Valid User user) {
        return new CommonResult<>(userService.addUser(user));
    }

    @ApiOperation("查询用户")
    @GetMapping("/queryUser")
    public CommonResult<User> getUser() {
        return new CommonResult<>(userService.findUser());
    }
}