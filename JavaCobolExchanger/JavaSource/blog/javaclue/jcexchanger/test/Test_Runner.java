package blog.javaclue.jcexchanger.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Test_Runner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(Test_Suite.class);
		for (Failure failure : result.getFailures()) {
			System.err.println(failure.toString());
		}
		System.out.println("Was test successful? " + result.wasSuccessful());
	}
}
