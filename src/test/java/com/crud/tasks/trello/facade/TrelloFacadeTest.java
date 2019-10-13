package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
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
        dtoBoardList.add(new TrelloBoardDto("test_board", "test_id", new ArrayList<>()));

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
}