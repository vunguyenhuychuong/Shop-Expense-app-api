package in.bushansirgur.expensetrackerapi.service;

import java.util.List;

import in.bushansirgur.expensetrackerapi.entity.Expense;

public interface ExpenseService {
	
	List<Expense> getAllExpenses();
}
