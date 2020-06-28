package com.webank.staging.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.webank.staging.common.utils.DataResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 验证码相关
 *
 */
@Api(tags = "验证码相关")
@RestController
@RequestMapping("/sys")
public class KaptchaController {
	
	private static final Logger log = LoggerFactory.getLogger(KaptchaController.class);
	
    //这里的captchaProducer要和KaptchaConfig里面的bean命名一样
    @Autowired
    private Producer captchaProducer;

    @ApiOperation(value = "生成验证码")
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //用字节数组存储
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        final HttpSession httpSession=request.getSession();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            //打印随机生成的字母和数字
            log.debug(createText);
            httpSession.setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
            responseOutputStream.write(captchaChallengeAsJpeg);
            responseOutputStream.flush();
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }finally {
            responseOutputStream.close();
        }

    }

    @ApiOperation(value = "校验验证码")
    @PostMapping(value = "/checkVerify")
    public DataResult checkVerify(HttpServletRequest request) {
        String captchaId = (String)
                request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String parameter = request.getParameter("imageCode");
        if (!captchaId.equals(parameter)) {
            return DataResult.fail("验证码输入有误");
        } else {
            return DataResult.success();
        }
    }

}
