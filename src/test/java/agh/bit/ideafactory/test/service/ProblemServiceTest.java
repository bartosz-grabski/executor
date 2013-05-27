package agh.bit.ideafactory.test.service;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.serviceimpl.ProblemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 27.05.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProblemServiceTest {

    @Mock
    private ProblemDao problemDao;

    @InjectMocks
    private ProblemService problemService = new ProblemServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDelegationToProblemDao() {
        long id = 1;
        problemService.getProblems();
        problemService.getById(id);

        verify(problemDao).getById(id);
        verify(problemDao).getProblems();
    }



}
