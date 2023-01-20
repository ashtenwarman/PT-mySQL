package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	//@formatter:off
	//creating menu
	private List<String> operations = List.of("1) Add a project");
	//@formatter:on
	//scanner to take inputs
	private Scanner sc = new Scanner(System.in);
	//creating Project Service object
	ProjectService projectService = new ProjectService();

	public static void main(String[] args) {
		new ProjectsApp().processUserSelections();
	}
	//method to process inputs, and check if it is a valid selection
	private void processUserSelections() {
		boolean done = false;

		while (!done) {
			try {
				int selection = getUserSelection();

				switch (selection) {
				case -1:
					done = exitMenu();
					break;

				case (1):
					createProject();
					break;

				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}
			} catch (Exception e) {
				System.out.println("\nError: " + e + "Try again.");
			}
		}

	}
	//method to exit menu and terminate program
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	//method to create a project with given inputs
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		Project project = new Project();
		
		//setters
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		//calling ProjectService to create row
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);

	}
	//converts input to a BigDecimal
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}
	//prints menu and gets user selection for menu input
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu selection: ");
		return Objects.isNull(input) ? -1 : input;
	}
	//converts input to Integer
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		if (Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}
	//converts input to String
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = sc.nextLine();
		return input.isBlank() ? null : input.trim();

	}
	//prints instructions for menu and available selections
	private void printOperations() {
		System.out.println("\nThese are the available selections, or press Enter to exit");
		operations.forEach(line -> System.out.println("   " + line));

	}

}