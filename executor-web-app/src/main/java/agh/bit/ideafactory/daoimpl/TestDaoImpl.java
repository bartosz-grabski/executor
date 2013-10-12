package agh.bit.ideafactory.daoimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;

@Repository("testDao")
public class TestDaoImpl extends BaseDaoImpl<Test> implements TestDao {

	public Long getHighestTestID() {
		Criteria criteria = getCriteria();
		criteria.setProjection(Projections.max("id"));
		return (Long) (criteria.uniqueResult() != null ? criteria.uniqueResult() : 0L);
	}

	@Override
	public List<Test> getTestByProblem(Problem problem) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("problem", problem));
		return criteria.list() != null ? (ArrayList<Test>) criteria.list() : new ArrayList<Test>();
	}

	@Override
	@Transactional
	public void saveTest(Test test, MultipartFile testInputFile, MultipartFile testOutputFile) throws IOException {
		byte[] testInputFileBlob = testInputFile.getBytes();
		byte[] testOutputFileBlob = testOutputFile.getBytes();

		test.setTestInputFile(testInputFileBlob);
		test.setTestOutputFile(testOutputFileBlob);

		sessionFactory.getCurrentSession().save(test);
	}

}
