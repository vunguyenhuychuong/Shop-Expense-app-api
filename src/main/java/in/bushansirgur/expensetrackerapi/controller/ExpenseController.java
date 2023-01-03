package in.bushansirgur.expensetrackerapi.controller;

import in.bushansirgur.expensetrackerapi.entity.Expense;
import in.bushansirgur.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;


@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping("/expenses")
	public Page<Expense> getAllExpenses(Pageable pageable) {
		return expenseService.getAllExpenses(pageable);
	}

	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable Long id){
		//return "The expense id is" + id;
		return expenseService.getExpenseById(id);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseById(@RequestParam("id") Long id){
		expenseService.getExpenseById(id);
		System.out.println("delete success");
	}


	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expense")
	public Expense saveExpenseDetails(@Valid  @RequestBody Expense expense) {
		return expenseService.saveExpenseDetails(expense);
	}


	@PutMapping("/expenses/{id}")
	public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id) {
		return expenseService.updateExpenseDetails(id, expense);
	}

	@GetMapping("/expenses/category")
	public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable page) {
		return expenseService.readByCategory(category, page);
	}

	@GetMapping("/expenses/name")
	public List<Expense> getExpensesByName(@RequestParam String keyword, Pageable page) {
		return expenseService.readByCategory(keyword, page);
	}

	@GetMapping("/expenses/date")
	public List<Expense> getExpenseByDates(@RequestParam(required = false) Date startDate,
										   @RequestParam(required = false) Date endDate,
										   Pageable page){
		return expenseService.readByDate(startDate, endDate, page);
	}

}
