package com.hoadeol.busybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

  /*레이아웃 적용한 기본 화면*/
  @GetMapping("basic")
  public String basic(Model model) {
    return "basic";
  }

  /*새 할일 추가*/
  @GetMapping("add_new_task")
  public String addNewTask() {
    return "add_new_task";
  }

  /*오늘의 할일 추가*/
  @GetMapping("add_today_task")
  public String addTodayTask() {
    return "add_today_task";
  }

  /*검색*/
  @GetMapping("search_task")
  public String searchTask() {
    return "search_task";
  }

  /*할일 리스트 조회*/
  /*CSS깨짐*/
  @GetMapping("task_list")
  public String taskList() {
    return "task_list";
  }

  /*이번주 할일 리스트 조회*/
  @GetMapping("week_task_list")
  public String weekTaskList() {
    return "week_task_list";
  }

  /*미뤄둔 할일 리스트 조회*/
  @GetMapping("pending_task_list")
  public String pendingTaskList() {
    return "pending_task_list";
  }

  /*모든 할일 리스트 조회*/
  @GetMapping("all_task_list")
  public String allTaskList() {
    return "all_task_list";
  }

  /*할일 상세조회*/
  @GetMapping("view_task_detail")
  public String viewTaskDetail() {
    return "view_task_detail";
  }

}
