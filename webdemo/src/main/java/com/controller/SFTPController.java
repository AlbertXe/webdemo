package com.controller;

import com.service.SFTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SFTPController {
    @Autowired
    SFTPService sftpService;

    @RequestMapping("/sftp/download")
    public String sftp() {
        log.info("SFTPController  开始sftp下载");
        sftpService.download("", "");
        return "ok";
    }
}
