package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoardTest() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "test_list", false);
        List<TrelloListDto> trelloLists = Arrays.asList(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "test", trelloLists);
        List<TrelloBoardDto> trelloBoards = Arrays.asList(trelloBoardDto);

        //When
        List<TrelloBoard> mappedList = trelloMapper.mapToBoard(trelloBoards);

        //Then
        Assert.assertEquals(trelloBoards.get(0).getId(), mappedList.get(0).getId());
    }

    @Test
    public void mapToBoardsDtoTest() {
        //Given
        TrelloList trelloList = new TrelloList("1", "test_list", false);
        List<TrelloList> trelloLists = Arrays.asList(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("1", "test", trelloLists);
        List<TrelloBoard> trelloBoards = Arrays.asList(trelloBoard);

        //When
        List<TrelloBoardDto> mappedList = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        Assert.assertEquals(trelloBoards.get(0).getId(), mappedList.get(0).getId());
    }

    @Test
    public void mapToListTest() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "test_list", false);
        List<TrelloListDto> trelloLists = Arrays.asList(trelloListDto);

        //When
        List<TrelloList> mappedList = trelloMapper.mapToList(trelloLists);

        //Then
        Assert.assertEquals(trelloLists.get(0).getId(), mappedList.get(0).getId());
    }

    @Test
    public void mapToListDtoTest() {
        //Given
        TrelloList trelloList = new TrelloList("1", "test_list", false);
        List<TrelloList> trelloLists = Arrays.asList(trelloList);

        //When
        List<TrelloListDto> mappedList = trelloMapper.mapToListDto(trelloLists);

        //Then
        Assert.assertEquals(trelloLists.get(0).getId(), mappedList.get(0).getId());
    }
}