package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloValidator trelloValidator;

    @Test
    public void shouldFetchTrelloBoards(){
        //Given
        List<TrelloListDto> dtoList = new ArrayList<>();
        dtoList.add(new TrelloListDto("test_id", "test_list", false));

        List<TrelloBoardDto> dtoBoardList = new ArrayList<>();
        dtoBoardList.add(new TrelloBoardDto("test_id", "test_board", new ArrayList<>()));

        List<TrelloList> mappedList = new ArrayList<>();
        mappedList.add(new TrelloList("test_id", "test_list", false));

        List<TrelloBoard> mappedBoardList = new ArrayList<>();
        mappedBoardList.add(new TrelloBoard("test_id", "test_board", mappedList));

        when(trelloService.fetchTrelloBoards()).thenReturn(dtoBoardList);
        when(trelloMapper.mapToBoard(dtoBoardList)).thenReturn(mappedBoardList);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(dtoBoardList);
        when(trelloValidator.validateTrelloBoards(mappedBoardList)).thenReturn(mappedBoardList);


        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloFacade.fetchTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shoudlFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoard(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldCreateCreatedTrelloCardDto() {
        //Given
        TrelloBadgesDto trelloBadgesDto = new TrelloBadgesDto(1, new TrelloAttachmentsByTypeDto());
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1", "test","test.com", trelloBadgesDto);
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test_name", "test","top", "1");
        TrelloCard trelloCard = new TrelloCard(
                "test_name", "test","top", "1");

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createdTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto createdTrelloCard = trelloFacade.createCard(trelloCardDto);

        //Then
        assertEquals("1",createdTrelloCard.getId());
        assertEquals("test",createdTrelloCard.getName());
        assertEquals("test.com",createdTrelloCard.getShortUrl());
        assertEquals(1, createdTrelloCard.getBadges().getVotes());

        assertEquals("test_name",trelloCard.getName());
        assertEquals("test",trelloCard.getDescription());
        assertEquals("top",trelloCard.getPos());
        assertEquals("1",trelloCard.getListId());


    }
}