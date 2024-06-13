import java.util.*;

class Employee {
    private String name;
    private int id;
    private String position;
    private String department;
    private double salary;
    private Employee supervisor;
    private List<Employee> subordinates;
    private Map<String, Object> performanceMetrics;

    public Employee(String name, int id, String position, String department, double salary, Employee supervisor) {
        this.name = name;
        this.id = id;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.supervisor = supervisor;
        this.subordinates = new ArrayList<>();
        this.performanceMetrics = new HashMap<>();
    }

    public void addEmployee(Employee emp) {
        subordinates.add(emp);
    }

    public void removeEmployee(Employee emp) {
        subordinates.remove(emp);
    }

    public void updateInformation(String name, String position, String department, double salary) {
        this.name = name;
        this.position = position;
        this.department = department;
        this.salary = salary;
    }

    public void changeDepartment(String newDepartment) {
        this.department = newDepartment;
    }

    public void promote(String newPosition, double newSalary) {
        this.position = newPosition;
        this.salary = newSalary;
    }

    public void demote(String newPosition, double newSalary) {
        this.position = newPosition;
        this.salary = newSalary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setPerformanceMetric(String metric, Object value) {
        performanceMetrics.put(metric, value);
    }

    public Map<String, Object> getPerformanceMetrics() {
        return performanceMetrics;
    }

    public double getSalary() {
        return salary;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }
}

class Hierarchy {
    private Map<Integer, Employee> employeeMap;

    public Hierarchy() {
        this.employeeMap = new HashMap<>();
    }

    public void buildHierarchy(List<Employee> employees) {
        for (Employee emp : employees) {
            employeeMap.put(emp.getId(), emp);
            if (emp.getSupervisor() != null) {
                emp.getSupervisor().addEmployee(emp);
            }
        }
    }

    public Employee findSupervisor(int employeeId) {
        Employee emp = employeeMap.get(employeeId);
        return emp != null ? emp.getSupervisor() : null;
    }

    public List<Employee> findSubordinates(int employeeId) {
        Employee emp = employeeMap.get(employeeId);
        return emp != null ? emp.getSubordinates() : new ArrayList<>();
    }

    public void updateHierarchy(Employee emp) {
        if (emp.getSupervisor() != null) {
            emp.getSupervisor().removeEmployee(emp);
        }
        Employee newSupervisor = findNewSupervisor(emp);
        emp.setSupervisor(newSupervisor);
        if (newSupervisor != null) {
            newSupervisor.addEmployee(emp);
        }
    }

    private Employee findNewSupervisor(Employee emp) {
        for (Employee supervisor : employeeMap.values()) {
            if (supervisor.getSubordinates().size() < 5) {
                return supervisor;
            }
        }
        return null;
    }

    public void analyzeStructure() {
        int maxDepth = 0;
        for (Employee emp : employeeMap.values()) {
            int depth = calculateHierarchyDepth(emp);
            if (depth > maxDepth) {
                maxDepth = depth;
            }
        }
        System.out.println("Hierarchy depth: " + maxDepth);
    }

    private int calculateHierarchyDepth(Employee emp) {
        if (emp == null || emp.getSubordinates().isEmpty()) {
            return 0;
        }
        int maxDepth = 0;
        for (Employee subordinate : emp.getSubordinates()) {
            int depth = calculateHierarchyDepth(subordinate);
            if (depth > maxDepth) {
                maxDepth = depth;
            }
        }
        return maxDepth + 1;
    }
}

class Department {
    private String name;
    private int id;
    private Employee manager;
    private List<Employee> employees;
    private double budget;

    public Department(String name, int id, Employee manager, double budget) {
        this.name = name;
        this.id = id;
        this.manager = manager;
        this.employees = new ArrayList<>();
        this.budget = budget;
    }

    public void assignEmployee(Employee emp) {
        employees.add(emp);
    }

    public void manageBudget(double newBudget) {
        this.budget = newBudget;
    }

    public void analyzePerformance() {
        double totalPerformance = 0;
        for (Employee emp : employees) {
            Map<String, Object> performanceMetrics = emp.getPerformanceMetrics();
            totalPerformance += (double) performanceMetrics.getOrDefault("productivity", 0.0);
        }
        double averagePerformance = totalPerformance / employees.size();
        System.out.println("Average performance for department " + name + ": " + averagePerformance);
    }
}

class PerformanceTracking {
    private Map<Integer, Map<String, Object>> performanceData;

    public PerformanceTracking() {
        this.performanceData = new HashMap<>();
    }

    public void trackPerformance(int employeeId, String metric, Object value) {
        performanceData.putIfAbsent(employeeId, new HashMap<>());
        performanceData.get(employeeId).put(metric, value);
    }

    public void generateReports() {
        for (Map.Entry<Integer, Map<String, Object>> entry : performanceData.entrySet()) {
            int employeeId = entry.getKey();
            Map<String, Object> metrics = entry.getValue();
            System.out.println("Performance report for employee ID " + employeeId + ":");
            for (Map.Entry<String, Object> metricEntry : metrics.entrySet()) {
                System.out.println(metricEntry.getKey() + ": " + metricEntry.getValue());
            }
            System.out.println();
        }
    }

    public void identifyTopPerformers() {
        Employee topPerformer = null;
        double maxProductivity = 0;
        for (Map.Entry<Integer, Map<String, Object>> entry : performanceData.entrySet()) {
            int employeeId = entry.getKey();
            Map<String, Object> metrics = entry.getValue();
            double productivity = (double) metrics.getOrDefault("productivity", 0.0);
            if (productivity > maxProductivity) {
                maxProductivity = productivity;
                topPerformer = new Employee("Employee " + employeeId, employeeId, "", "", 0, null);
            }
        }
        if (topPerformer != null) {
            System.out.println("Top performer: " + topPerformer.getName() + " (Productivity: " + maxProductivity + ")");
        }
    }
}

class Security {
    private Map<String, String> userCredentials;
    private Map<String, String> accessControlPolicies;

    public Security() {
        this.userCredentials = new HashMap<>();
        this.accessControlPolicies = new HashMap<>();
    }

    public boolean authenticateUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    public void enforceAccessControl(String username, String policy) {
        if (userCredentials.containsKey(username) && accessControlPolicies.containsKey(username)) {
            String userPolicy = accessControlPolicies.get(username);
            if (userPolicy.equals(policy)) {
                System.out.println("Access granted");
                return;
            }
        }
        System.out.println("Access denied");
    }

    public void encryptData() {
        System.out.println("Encrypting sensitive data...");
       
    }

    public void changeCredentials(String username, String newPassword) {
        userCredentials.put(username, newPassword);
    }
}

public class EmployeeManagementSystem {
    private static List<Employee> employees;
    private static Hierarchy hierarchy;
    private static List<Department> departments;
    private static PerformanceTracking performanceTracking;
    private static Security security;

    public static void main(String[] args) {
        employees = new ArrayList<>();
        hierarchy = new Hierarchy();
        departments = new ArrayList<>();
        performanceTracking = new PerformanceTracking();
        security = new Security();

     
        Employee manager1 = new Employee("Arihant", 1, "Manager", "HR", 10000, null);
        Employee employee1 = new Employee("Srashti", 2, "Associate", "HR", 5000, manager1);
        Employee employee2 = new Employee("Tanu", 3, "Associate", "HR", 5000, manager1);

        employees.add(manager1);
        employees.add(employee1);
        employees.add(employee2);

        hierarchy.buildHierarchy(employees);

        Department hrDepartment = new Department("HR", 1, manager1, 50000);
        hrDepartment.assignEmployee(manager1);
        hrDepartment.assignEmployee(employee1);
        hrDepartment.assignEmployee(employee2);

        departments.add(hrDepartment);

   
        Employee emp = hierarchy.findSupervisor(2);
        if (emp != null) {
            System.out.println("Supervisor of employee 2 is: " + emp.getName());
        }

       
        hierarchy.updateHierarchy(employee2);

      
        hierarchy.analyzeStructure();

        performanceTracking.trackPerformance(2, "productivity", 85.5);
        performanceTracking.trackPerformance(3, "productivity", 90.0);

       
        performanceTracking.generateReports();

        // Identify top performers
        performanceTracking.identifyTopPerformers();

        // Manage security
        security.changeCredentials("user1", "password123");
        boolean isAuthenticated = security.authenticateUser("user1", "password123");
        System.out.println("User authenticated: " + isAuthenticated);
        security.enforceAccessControl("user1", "admin");
        security.encryptData();
    }
}
