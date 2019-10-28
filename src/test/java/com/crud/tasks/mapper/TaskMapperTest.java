package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    @InjectMocks
    TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {
        //Given
        Task task = new Task(1L, "name", "description");
        TaskDto taskDto = new TaskDto(1L, "name", "description");

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(task.getId(), mappedTask.getId());
        assertEquals(task.getTitle(), mappedTask.getTitle());
        assertEquals(task.getContent(), mappedTask.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        //Given
        Task task = new Task(1L, "name", "description");
        TaskDto taskDto = new TaskDto(1L, "name", "description");

        //When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(taskDto.getId(), mappedTaskDto.getId());
        assertEquals(taskDto.getTitle(), mappedTaskDto.getTitle());
        assertEquals(taskDto.getContent(), mappedTaskDto.getContent());
    }

    @Test
    public void mapTaskDtoListTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "name", "description");
        List<TaskDto> taskDtoList = Arrays.asList(taskDto);

        Task task = new Task(1L, "name", "description");
        List<Task> taskList = Arrays.asList(task);

        //When
        List<TaskDto> mappedList = taskMapper.mapTaskDtoList(taskList);

        //Then
        mappedList.forEach(t -> {
            assertEquals(taskDto.getId(), t.getId());
            assertEquals(taskDto.getTitle(), t.getTitle());
            assertEquals(taskDto.getContent(),t.getContent());
        });
    }
}
