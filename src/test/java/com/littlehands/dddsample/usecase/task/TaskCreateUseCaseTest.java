package com.littlehands.dddsample.usecase.task;

import com.littlehands.dddsample.domain.task.*;
import com.littlehands.dddsample.domain.user.UserMockRepository;
import com.littlehands.dddsample.domain.user.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class TaskCreateUseCaseTest {
  private UserRepository userRepository;
  private TaskRepository taskRepository;
  private TaskCreateUseCase taskCreateUseCase;

  TaskCreateUseCaseTest() {
    this.userRepository = new UserMockRepository();
    this.taskRepository = new TaskMockRepository();
    this.taskCreateUseCase = new TaskCreateUseCase(this.taskRepository, this.userRepository);
  }

  @Test
  void createTaskTest() {
    // when
    // タスクを作成すると
    String taskName = "name";
    LocalDate dueDate = LocalDate.of(2020, 1, 1);
    TaskId createdTaskId = taskCreateUseCase.createTask(taskName, dueDate);

    // then
    Task createdTask = taskRepository.findById(createdTaskId);

    // 名前、期日が入力した値になっている
    assertThat(createdTask.getName(), is(taskName));
    assertThat(createdTask.getDueDate(), is(dueDate));
    // ステータスが「未完了」、延期回数が0回になっている
    assertThat(createdTask.getTaskStatus(), is(TaskStatus.UNDONE));
    assertThat(createdTask.getPostponeCount(), is(0));
  }

}