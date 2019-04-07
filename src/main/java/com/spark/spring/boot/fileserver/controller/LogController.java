package com.spark.spring.boot.fileserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/log")
public class LogController {
        private Logger logger = LoggerFactory.getLogger(LogController.class);

        @GetMapping("/info")
        public String info() {
            logger.info("Info log");
            return "info";
        }
}
