package org.tradebyte.todolist;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("org.tradebyte.todolist")
@IncludeClassNamePatterns(".*Test")
public class ToDoListTestSuite {

}
