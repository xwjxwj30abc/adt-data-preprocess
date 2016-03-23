package zx.soft.adt.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import zx.soft.adt.domain.AccessList;

public class SQLOperationTest {

	@Test
	public void test() {

		SQLOperation sqlOperation = new SQLOperation();
		List<AccessList> list = sqlOperation.getAccessListData("AccessList", 0);
		assertEquals(34010129000002L, list.get(0).getService_code());
	}

}
