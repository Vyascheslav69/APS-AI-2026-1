package dao;

import solver.instance.Instance;

public class TestDao {

	public static void main(String[] args) {
		// DAO.readInstance("files/instances/gr48.tsp/gr48.tsp");
		// DAO.readInstance("files/instances/brazil58.tsp/brazil58.tsp");
		// DAO.readInstance("files/instances/a280.tsp/a280.tsp");
		// DAO.readInstance("files/instances/bayg29.tsp/bayg29.tsp");
		DAO.readInstance("files/instances/gr17.tsp/gr17.tsp");
		System.out.println(Instance.inst);
	}

}
