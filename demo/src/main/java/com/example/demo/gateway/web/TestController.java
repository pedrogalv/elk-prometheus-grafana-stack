package com.example.demo.gateway.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;

@Slf4j
@RestController
public class TestController {

  @GetMapping(path = "/info")
  public String infoCall() {
    log.info("info call made", StructuredArguments.value("infoParam", "InfoParam1"));
    return "Info";
  }

  @GetMapping(path = "/warn")
  public String warnCall() {
    log.warn("warn call made", StructuredArguments.value("warnParam", "WarnParam1"));
    return "Warn";
  }

  @GetMapping(path = "/error")
  public String errorCall() {
    log.error("error call made", StructuredArguments.value("errorParam", "ErrorParam1"));
    return "Error222";
  }
}
