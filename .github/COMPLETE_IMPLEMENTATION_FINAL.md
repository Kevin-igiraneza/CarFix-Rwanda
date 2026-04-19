# CARFIX RWANDA - COMPLETE IMPLEMENTATION CODE
## Sections 1-3: Security + Java 21 + UI/UX

**Status**: Ready to implement - All code provided here  
**Total Time**: 2-3 hours  
**Risk**: Low (reversible, incremental)

---

# SECTION 1: UPDATE pom.xml (SECURITY + JAVA 21)

**File**: `pom.xml`

Replace the ENTIRE file with this content (COPY-PASTE):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.2</version> <!-- JAVA 21 COMPATIBLE -->
        <relativePath/>
    </parent>

    <groupId>com.carfix</groupId>
    <artifactId>carfix-rwanda</artifactId>
    <version>1.0.0</version>
    <name>CarFix Rwanda</name>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <log4j.version>2.20.0</log4j.version>
        <jackson.version>2.15.2</jackson.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <release>21</release>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
            </plugin>
        </plugins>
    </build>
</project>
```

---

# SECTION 2: GLOBAL CSS STYLESHEET

**Directory**: Create if not exists: `src/main/resources/static/css/`  
**File**: `style.css`

COPY this entire CSS file into `src/main/resources/static/css/style.css`:

```css
/* Global Styles - CarFix Rwanda */
:root {
    --primary: #2c3e50;
    --secondary: #3498db;
    --accent: #e74c3c;
    --success: #27ae60;
    --warning: #f39c12;
    --gray-light: #ecf0f1;
    --gray-dark: #34495e;
    --border-radius: 8px;
    --box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: var(--gray-dark);
    background-color: #f8f9fa;
    line-height: 1.6;
}

.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 1.5rem;
}

/* ========== NAVBAR ========== */
.navbar {
    background-color: var(--primary);
    color: white;
    padding: 1rem 0;
    box-shadow: var(--box-shadow);
    position: sticky;
    top: 0;
    z-index: 1000;
}

.navbar .container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.navbar-brand {
    font-size: 1.5rem;
    font-weight: bold;
    color: white;
}

.navbar-menu {
    display: flex;
    gap: 2rem;
    align-items: center;
}

.navbar-menu a {
    color: white;
    text-decoration: none;
    transition: color 0.3s ease;
    font-weight: 500;
}

.navbar-menu a:hover {
    color: var(--secondary);
}

.user-name {
    color: white;
    font-weight: 600;
    margin-right: 1rem;
}

.logout-btn {
    background-color: var(--accent);
    color: white;
    padding: 0.6rem 1.2rem;
    border-radius: 5px;
    border: none;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.logout-btn:hover {
    background-color: #c0392b;
}

/* ========== CARDS ========== */
.card {
    background: white;
    border-radius: var(--border-radius);
    padding: 2rem;
    box-shadow: var(--box-shadow);
    margin-bottom: 2rem;
    transition: all 0.3s ease;
}

.card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* ========== BUTTONS ========== */
.btn {
    display: inline-block;
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 5px;
    font-weight: 600;
    cursor: pointer;
    text-decoration: none;
    transition: all 0.3s ease;
    font-size: 1rem;
}

.btn-primary {
    background-color: var(--secondary);
    color: white;
}

.btn-primary:hover {
    background-color: #2980b9;
    transform: translateY(-2px);
}

.btn-success {
    background-color: var(--success);
    color: white;
}

.btn-success:hover {
    background-color: #229954;
}

.btn-danger {
    background-color: var(--accent);
    color: white;
}

.btn-danger:hover {
    background-color: #c0392b;
}

.btn-secondary {
    background-color: #95a5a6;
    color: white;
}

.btn-secondary:hover {
    background-color: #7f8c8d;
}

/* ========== FORMS ========== */
.form-group {
    margin-bottom: 1.5rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 600;
    color: var(--primary);
}

.form-control {
    width: 100%;
    padding: 0.75rem 1rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
    font-family: inherit;
    transition: border-color 0.3s ease;
}

.form-control:focus {
    outline: none;
    border-color: var(--secondary);
    box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.1);
}

/* ========== TABLES ========== */
.table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 1.5rem;
}

.table thead {
    background-color: var(--primary);
    color: white;
}

.table th {
    padding: 1rem;
    text-align: left;
    font-weight: 600;
}

.table td {
    padding: 0.75rem 1rem;
    border-bottom: 1px solid var(--gray-light);
}

.table tbody tr:hover {
    background-color: #f5f5f5;
}

/* ========== GRID ========== */
.row {
    display: flex;
    flex-wrap: wrap;
    gap: 1.5rem;
}

.col-md-3 { flex: 0 0 calc(25% - 1.5rem); }
.col-md-4 { flex: 0 0 calc(33.333% - 1.5rem); }
.col-md-6 { flex: 0 0 calc(50% - 1.5rem); }
.col-md-12 { flex: 0 0 100%; }

/* ========== STATS ========== */
.stat {
    font-size: 2.5rem;
    font-weight: bold;
    color: var(--secondary);
    margin: 1rem 0;
}

.stat-label {
    color: #999;
    font-size: 0.9rem;
    text-transform: uppercase;
    letter-spacing: 1px;
}

/* ========== MECHANICS GRID ========== */
.mechanics-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1.5rem;
    margin: 2rem 0;
}

.mechanic-card {
    text-align: center;
}

.mechanic-card h3 {
    color: var(--primary);
    margin-bottom: 0.5rem;
}

.mechanic-card .rating {
    color: var(--warning);
    font-weight: bold;
    margin: 0.5rem 0;
}

.mechanic-card .specialization {
    color: #999;
    font-size: 0.95rem;
    margin-bottom: 1rem;
}

/* ========== SEARCH FORM ========== */
.search-form {
    display: flex;
    gap: 1rem;
    align-items: flex-end;
}

.search-form .form-group {
    flex: 1;
    margin-bottom: 0;
}

.search-form .btn {
    white-space: nowrap;
}

/* ========== RESPONSIVE ========== */
@media (max-width: 1200px) {
    .container { padding: 0 1rem; }
    .col-md-3 { flex: 0 0 calc(50% - 0.75rem); }
    .col-md-4 { flex: 0 0 calc(50% - 0.75rem); }
}

@media (max-width: 768px) {
    .navbar .container {
        flex-direction: column;
        gap: 1rem;
    }

    .navbar-menu {
        gap: 1rem;
        flex-wrap: wrap;
        justify-content: center;
    }

    .container {
        padding: 0 1rem;
    }

    .card {
        padding: 1.5rem;
    }

    .row {
        gap: 1rem;
    }

    .col-md-3,
    .col-md-4,
    .col-md-6,
    .col-md-12 {
        flex: 0 0 100%;
    }

    .search-form {
        flex-direction: column;
    }

    .search-form .btn {
        width: 100%;
    }

    .mechanics-grid {
        grid-template-columns: 1fr;
    }

    h1 { font-size: 1.75rem; }
    h2 { font-size: 1.5rem; }
}

@media (max-width: 480px) {
    body { font-size: 14px; }
    .navbar { padding: 0.75rem 0; }
    .navbar-brand { font-size: 1.25rem; }
    .container { padding: 0 0.75rem; }
    .card { padding: 1rem; }
    
    h1 { font-size: 1.5rem; }
    h2 { font-size: 1.25rem; }
    
    .btn {
        padding: 0.5rem 1rem;
        font-size: 0.9rem;
    }

    .stat {
        font-size: 1.75rem;
    }

    .table {
        font-size: 0.8rem;
    }

    .table th, .table td {
        padding: 0.4rem;
    }
}
```

---

# SECTION 3: NAVBAR FRAGMENT

**Directory**: Create if needed: `src/main/resources/templates/fragments/`  
**File**: `navbar.html`

COPY this into `src/main/resources/templates/fragments/navbar.html`:

```html
<div th:fragment="navbar" xmlns:th="http://www.thymeleaf.org" 
     xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
    <nav class="navbar">
        <div class="container">
            <div class="navbar-brand">
                <a href="/" style="color: white; text-decoration: none;">🔧 CarFix Rwanda</a>
            </div>
            <div class="navbar-menu">
                <!-- NOT authenticated -->
                <span sec:authorize="!isAuthenticated()" class="auth-links">
                    <a href="/login">Login</a>
                    <a href="/register">Register</a>
                </span>

                <!-- IS authenticated -->
                <span sec:authorize="isAuthenticated()" class="user-menu">
                    <span sec:authorize="hasRole('ADMIN')">
                        <a href="/admin/dashboard">Dashboard</a>
                    </span>
                    <span sec:authorize="hasRole('MECHANIC')">
                        <a href="/mechanic/dashboard">Dashboard</a>
                    </span>
                    <span sec:authorize="hasRole('CUSTOMER')">
                        <a href="/customer/dashboard">Dashboard</a>
                    </span>
                    
                    <span class="user-name" sec:authentication="name"></span>
                    
                    <form method="post" action="/logout" style="display: inline;">
                        <button type="submit" class="logout-btn">Logout</button>
                    </form>
                </span>
            </div>
        </div>
    </nav>
</div>
```

---

# SECTION 4: ADMIN DASHBOARD TEMPLATE

**Directory**: `src/main/resources/templates/admin/`  
**File**: `dashboard.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - CarFix Rwanda</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div th:replace="fragments/navbar :: navbar"></div>
    
    <div class="container" style="margin-top: 2rem;">
        <h1>Admin Dashboard</h1>
        
        <!-- Stats Row -->
        <div class="row">
            <div class="col-md-3">
                <div class="card stat-card">
                    <h3 class="stat" th:text="${totalUsers ?: 0}">0</h3>
                    <p class="stat-label">Total Users</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card">
                    <h3 class="stat" th:text="${totalMechanics ?: 0}">0</h3>
                    <p class="stat-label">Total Mechanics</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card">
                    <h3 class="stat" th:text="${pendingRequests ?: 0}">0</h3>
                    <p class="stat-label">Pending Requests</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card">
                    <h3 class="stat" th:text="${completedRequests ?: 0}">0</h3>
                    <p class="stat-label">Completed</p>
                </div>
            </div>
        </div>

        <!-- Service Requests Table -->
        <div class="card">
            <h2>Service Requests</h2>
            <div style="overflow-x: auto;">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Customer</th>
                            <th>Vehicle</th>
                            <th>Status</th>
                            <th>Mechanic</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr th:each="request : ${recentRequests ?: #lists.isEmpty(recentRequests)}">
                            <td th:text="${request.customer.name}">N/A</td>
                            <td th:text="${request.vehicle.model}">N/A</td>
                            <td>
                                <span class="badge" th:classappend="${request.status == 'COMPLETED' ? 'badge-success' : 'badge-warning'}"
                                      th:text="${request.status}">PENDING</span>
                            </td>
                            <td th:text="${request.mechanic?.name ?: 'Unassigned'}">Unassigned</td>
                            <td>
                                <a th:href="@{/admin/requests/{id}(id=${request.id})}" class="btn btn-sm btn-primary">View</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
```

---

# SECTION 5: CUSTOMER FIND MECHANIC

**Directory**: `src/main/resources/templates/customer/`  
**File**: `find-mechanic.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Find a Mechanic - CarFix Rwanda</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div th:replace="fragments/navbar :: navbar"></div>
    
    <div class="container" style="margin-top: 2rem;">
        <h1>Find a Mechanic</h1>
        
        <!-- Search Form -->
        <div class="card">
            <form method="get" class="search-form">
                <div class="form-group">
                    <input type="text" name="search" class="form-control" 
                           placeholder="Search by specialization or name..." 
                           th:value="${searchQuery}">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>

        <!-- Mechanics Grid -->
        <div class="mechanics-grid">
            <div class="card mechanic-card" th:each="mechanic : ${mechanics}">
                <h3 th:text="${mechanic.name}">John Doe</h3>
                <p class="specialization" th:text="${mechanic.specialization}">Engine Repair</p>
                <p class="rating">
                    ⭐ <span th:text="${mechanic.rating ?: '5'}">5</span>/5
                </p>
                <a th:href="@{/customer/request-service(mechanicId=${mechanic.id})}" 
                   class="btn btn-primary">Request Service</a>
            </div>
        </div>

        <div th:if="${#lists.isEmpty(mechanics)}" class="card" style="text-align: center;">
            <p>No mechanics found. Try another search.</p>
        </div>
    </div>
</body>
</html>
```

---

# SECTION 6: MECHANIC DASHBOARD

**Directory**: `src/main/resources/templates/mechanic/`  
**File**: `dashboard.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mechanic Dashboard - CarFix Rwanda</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div th:replace="fragments/navbar :: navbar"></div>
    
    <div class="container" style="margin-top: 2rem;">
        <h1>My Dashboard</h1>
        
        <!-- Real Stats -->
        <div class="row">
            <div class="col-md-3">
                <div class="card stat-card">
                    <p class="stat-label">Total Revenue</p>
                    <h3 class="stat" th:text="'RWF ' + (${totalRevenue ?: 0})">RWF 0</h3>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card">
                    <p class="stat-label">Completed This Month</p>
                    <h3 class="stat" th:text="${completedThisMonth ?: 0}">0</h3>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card">
                    <p class="stat-label">Average Rating</p>
                    <h3 class="stat" th:text="${averageRating ?: '5.0'}">5.0</h3>/5
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card">
                    <p class="stat-label">Pending Requests</p>
                    <h3 class="stat" th:text="${pendingCount ?: 0}">0</h3>
                </div>
            </div>
        </div>

        <!-- My Requests -->
        <div class="card">
            <h2>Recent Requests</h2>
            <div style="overflow-x: auto;">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Customer</th>
                            <th>Vehicle</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr th:each="request : ${myRequests ?: #lists.isEmpty(myRequests)}">
                            <td th:text="${request.customer.name}">N/A</td>
                            <td th:text="${request.vehicle.model}">N/A</td>
                            <td>
                                <span class="badge badge-warning" th:text="${request.status}">IN_PROGRESS</span>
                            </td>
                            <td>
                                <a th:href="@{/mechanic/requests/{id}(id=${request.id})}" 
                                   class="btn btn-sm btn-primary">View</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
```

---

# SECTION 7: UPDATE AdminController.java

**File**: `src/main/java/com/carfix/controller/AdminController.java`

Add these methods to get real statistics:

```java
@GetMapping("/dashboard")
public String dashboard(Model model) {
    // Add real data from database
    model.addAttribute("totalUsers", userService.countAllUsers());
    model.addAttribute("totalMechanics", mechanicService.countAll());
    model.addAttribute("pendingRequests", serviceRequestService.countByStatus("PENDING"));
    model.addAttribute("completedRequests", serviceRequestService.countCompletedThisMonth());
    model.addAttribute("recentRequests", serviceRequestService.findRecent(10));
    
    return "admin/dashboard";
}

@GetMapping("/requests/{id}")
public String viewRequest(@PathVariable Long id, Model model) {
    ServiceRequest request = serviceRequestService.findById(id);
    model.addAttribute("request", request);
    model.addAttribute("mechanics", mechanicService.findAll());
    return "admin/request-detail";
}

@PostMapping("/requests/{id}/assign")
public String assignMechanic(@PathVariable Long id, @RequestParam Long mechanicId) {
    serviceRequestService.assignMechanic(id, mechanicId);
    return "redirect:/admin/requests/" + id;
}
```

---

# SECTION 8: UPDATE MechanicController.java

**File**: `src/main/java/com/carfix/controller/MechanicController.java`

```java
@GetMapping("/dashboard")
public String dashboard(Authentication auth, Model model) {
    Mechanic mechanic = mechanicService.findByUsername(auth.getName());
    
    // Real revenue calculation from completed requests
    BigDecimal totalRevenue = serviceRequestService
        .findCompletedByMechanic(mechanic)
        .stream()
        .map(ServiceRequest::getPaymentAmount)
        .filter(Objects::nonNull)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    // Real completion count
    long completedThisMonth = serviceRequestService.countCompletedThisMonth(mechanic);
    
    // Real rating from reviews
    double avgRating = reviewService.findByMechanic(mechanic)
        .stream()
        .mapToInt(Review::getRating)
        .average()
        .orElse(5.0);
    
    model.addAttribute("totalRevenue", totalRevenue);
    model.addAttribute("completedThisMonth", completedThisMonth);
    model.addAttribute("averageRating", String.format("%.1f", avgRating));
    model.addAttribute("pendingCount", serviceRequestService.countPendingByMechanic(mechanic));
    model.addAttribute("myRequests", serviceRequestService.findRecentByMechanic(mechanic, 10));
    
    return "mechanic/dashboard";
}
```

---

# EXECUTION CHECKLIST

**Phase 1: Security & Java 21**
- [ ] Backup current pom.xml
- [ ] Replace pom.xml with updated version (Section 1)
- [ ] Run: `mvnw.cmd clean compile`
- [ ] Verify no compile errors
- [ ] Run: `mvnw.cmd clean test` (if tests exist)

**Phase 2: CSS & Navbar**
- [ ] Create directories: `src/main/resources/static/css/`
- [ ] Create directories: `src/main/resources/templates/fragments/`
- [ ] Add style.css (Section 2)
- [ ] Add navbar.html fragment (Section 3)

**Phase 3: Templates & Controllers**
- [ ] Create admin templates directory
- [ ] Add admin/dashboard.html (Section 4)
- [ ] Create customer templates directory
- [ ] Add customer/find-mechanic.html (Section 5)
- [ ] Create mechanic templates directory
- [ ] Add mechanic/dashboard.html (Section 6)
- [ ] Update AdminController (Section 7)
- [ ] Update MechanicController (Section 8)

**Phase 4: Build & Test**
- [ ] Run: `mvnw.cmd clean compile`
- [ ] Run: `mvnw.cmd spring-boot:run`
- [ ] Test dashboard access
- [ ] Verify responsive design on mobile
- [ ] Test all workflows

---

# SUMMARY

✅ **All three improvements in one plan**:
- Security: Updated dependencies, Java 21 compatible
- Java 21: Compiler plugin 3.11.0+, source/target set to 21
- UI/UX: Responsive grid, real data, clean styling

✅ **Status**: READY FOR COPY-PASTE IMPLEMENTATION

Total time: 2-3 hours
Risk: LOW (reversible, incremental)

