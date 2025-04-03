package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api("店铺相关接口")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为{}",status == 1?"营业中":"打烊");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获得店铺状态")
    public Result<Integer> getStatus(){
        log.info("获得店铺状态...");

        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        return Result.success(status);

    }

}
