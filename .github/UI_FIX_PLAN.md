# CarFix Rwanda - UI/UX Fix Plan

**Date**: 2026-04-19  
**Priority**: HIGH  
**Scope**: Dashboard & Frontend Improvements

---

## Issues to Fix

### 🔴 CRITICAL ISSUES

#### 1. Admin Dashboard
- **Issue**: Responsive design broken, looks bad
- **Fix**: Improve CSS, responsive grid layout, proper spacing
- **Files**: Admin templates, CSS files

#### 2. Customer Request Assignment
- **Issue**: Admin can assign mechanic, but workflow is unclear
- **Fix**: Clear UI for mechanic assignment workflow
- **Files**: Admin request management template

#### 3. Navigation Redundancy
- **Issue**: Sidebar has "Booking" and "Request" pointing to same page
- **Fix**: Remove duplicate, keep only "Service Requests"
- **Files**: Sidebar/navbar templates

#### 4. Customer Logout
- **Issue**: Logout button looks bad, poor CSS
- **Fix**: Style logout button/link properly
- **Files**: Navbar/header template, CSS

### 🟠 HIGH PRIORITY ISSUES

#### 5. Unauthorized Navigation Items (After Login)
- **Issue**: Login/Register buttons visible when already logged in
- **Fix**: Hide with `th:if="${currentUser != null}"` (or role-based logic)
- **Files**: Customer dashboard, navbar

#### 6. Register as Mechanic Link (Customer View)
- **Issue**: "Register as mechanic" shown when finding mechanic (confusing)
- **Fix**: Remove or make context-aware
- **Files**: Customer mechanic search template

#### 7. Sidebar Empty Sections
- **Issue**: Messages, Settings don't have content
- **Fix**: Either implement or hide them
- **Files**: Dashboard sidebar, routes

#### 8. Mechanic Dashboard Issues
- **Issue**: "Verified" directory, Messages, Settings, "My Queue" empty
- **Fix**: Remove or implement with real data
- **Files**: Mechanic dashboard templates

#### 9. Hardcoded Revenue & Targets
- **Issue**: "Total Revenue" and "Weekly Target" are hardcoded
- **Fix**: Calculate from real database data or hide
- **Files**: Mechanic dashboard controller, template

#### 10. Landing Page Support Center
- **Issue**: Content not relevant/good
- **Fix**: Update with proper support information
- **Files**: Landing page template

#### 11. Quick Search Not Working
- **Issue**: Quick search functionality broken
- **Fix**: Implement or improve search logic
- **Files**: Search component, controller, JavaScript

#### 12. Overall Design
- **Issue**: Inconsistent styling, not clean/simple
- **Fix**: Standardize colors, typography, spacing
- **Files**: CSS, all templates

---

## Implementation Plan

### PHASE 1: File Discovery
1. Identify template locations
2. Identify controller locations
3. Identify CSS files
4. Map current issues to files

### PHASE 2: Template Fixes
1. Fix responsive layout (Bootstrap improvements)
2. Fix navigation items visibility
3. Remove redundant menu items
4. Add proper CSS styling

### PHASE 3: Controller Fixes
1. Add user context to views
2. Implement real data queries (revenue, targets)
3. Fix search functionality
4. Add mechanic assignment workflow

### PHASE 4: CSS Improvements
1. Create clean, modern design
2. Improve responsive behavior
3. Better spacing and alignment
4. Consistent color scheme

### PHASE 5: Testing
1. Verify all pages load correctly
2. Test responsive design (mobile, tablet, desktop)
3. Test user workflows
4. Test build passes

---

## Expected Deliverables

✅ Clean, responsive admin dashboard  
✅ Proper customer request assignment workflow  
✅ Hidden unauthorized navigation items  
✅ Removed duplicate sidebar items  
✅ Implemented real data (revenue, assignments)  
✅ Fixed logout styling  
✅ Improved landing page  
✅ Working quick search  
✅ Modern, simple CSS design  
✅ Project builds successfully  

---

## Next Step

Ready to implement. Let me analyze the project structure first...

