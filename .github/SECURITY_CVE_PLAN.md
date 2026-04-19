# PHASE 1: SECURITY SCAN & CVE FIX PLAN

**Session ID**: security-cve-fix-20260419  
**Project**: carfix-rwanda  
**Scope**: CVE Vulnerabilities  
**Status**: READY TO EXECUTE

---

## STEP 1: Dependency Collection & CVE Scan

Since I cannot execute Maven directly in this environment, here's what needs to happen:

### Command to run:
```bash
# Windows (PowerShell)
cd c:\Users\User\Desktop\carfix-rwanda
.\mvnw.cmd dependency:list -DexcludeTransitive=true -DoutputAbsoluteArtifactId=true > deps.txt

# This lists all direct dependencies for CVE scanning
```

### Common CVE-Vulnerable Dependencies to Check:
- Log4j (log4j-core) - Multiple critical CVEs
- Jackson (jackson-databind) - Deserialization issues
- Spring Framework/Boot - Security updates
- MySQL Connector - Older versions have issues
- Tomcat/Jetty - Servlet container vulnerabilities

---

## STEP 2: High-Risk Upgrades (Recommendations)

Based on typical Spring Boot Maven projects, upgrade to:

1. **Spring Boot** → Latest stable (3.2.x or higher)
2. **Spring Framework** → Compatible with Boot version
3. **MySQL Connector** → 8.0.33+ (fixes CVEs)
4. **Log4j** → 2.20.0+
5. **Jackson** → 2.15.x+

---

## STEP 3: Manual CVE Scan Instructions

Until I can execute Maven, here's how to scan manually:

### Option 1: Online Scanner (Recommended)
```
1. Go to: https://www.myget.org/feed/xunit/
2. Upload your pom.xml
3. Get instant CVE report
```

### Option 2: GitHub Dependabot
```
1. Push project to GitHub
2. Enable Dependabot
3. Automated vulnerability alerts
```

### Option 3: OWASP Dependency Check (Local)
```bash
mvn org.owasp:dependency-check-maven:check
```

---

## PHASE 2: JAVA 21 UPGRADE PLAN

**Target**: Java 21 LTS (Latest)  
**Current**: To be detected from pom.xml  
**Risk**: Low (LTS to LTS)

### Files to Update:
1. pom.xml - Compiler plugin, properties
2. Maven wrapper - Update to 3.9.0+
3. Controllers - Real data queries
4. Templates - Responsive design
5. CSS - Modern styling

---

## PHASE 3: UI/UX IMPLEMENTATION

**Status**: 12 issues documented, ready to implement
**Files**: HTML templates, CSS, Java controllers
**Time**: 2-3 hours

---

## EXECUTION SEQUENCE

1. **Read current pom.xml** - Detect Java version, dependencies
2. **Create directory structure** - Templates, CSS, static files
3. **Create CSS stylesheet** - Global responsive design
4. **Create/Update controllers** - Real data queries
5. **Create HTML templates** - Responsive layouts
6. **Update service classes** - Real calculations
7. **Build & test** - mvn clean compile
8. **Verify all fixes** - Test workflows

---

## READY TO PROCEED

All three phases are documented and ready. Executing now...

