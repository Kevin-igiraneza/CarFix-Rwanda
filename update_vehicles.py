import re

with open('src/main/resources/templates/customer-dashboard.html', 'r', encoding='utf-8') as f:
    dashboard = f.read()

dashboard = dashboard.replace('<title>Customer Dashboard - CarFix Rwanda</title>', '<title>My Vehicles - Customer - CarFix Rwanda</title>')

dashboard = dashboard.replace('href="/customer-dashboard" class="active dashboard-menu-link"', 'href="/customer-dashboard" class="dashboard-menu-link"')
dashboard = dashboard.replace('href="/vehicle-registration" class="dashboard-menu-link"', 'href="/vehicle-registration" class="active dashboard-menu-link"')

dashboard = re.sub(r'<section class="customer-summary-grid">.*?</section>', '', dashboard, flags=re.DOTALL)

new_main = '''
<section class="customer-dashboard-grid">
    <div class="customer-left-column" style="grid-column: 1 / -1; max-width: 900px; margin: 0 auto; width: 100%;">
        
        <!-- Display Existing Vehicles -->
        <div class="customer-panel">
            <div class="panel-header">
                <h3 data-i18n="dash.my_vehicles">My Vehicles</h3>
            </div>
            <p th:if="${vehicles == null || vehicles.isEmpty()}" style="font-size:14px;line-height:1.6;">
                You have not registered any vehicle yet.
            </p>
            <div class="vehicle-grid" th:if="${vehicles != null && !vehicles.isEmpty()}">
                <div class="vehicle-card" th:each="vehicle : ${vehicles}">
                    <div class="vehicle-icon">&#128663;</div>
                    <h4 th:text="${vehicle.vehicleName}">Vehicle Name</h4>
                    <p>
                        Plate: <span th:text="${vehicle.plateNumber}">RAB 123 A</span>
                    </p>
                    <small>
                        <span th:text="${vehicle.year}">2022</span> &bull; 
                        <span th:text="${vehicle.fuelType}">Petrol</span> &bull; 
                        <span th:text="${vehicle.transmission}">Automatic</span>
                    </small>
                </div>
            </div>
        </div>

        <!-- Add New Vehicle Form -->
        <div class="customer-panel" style="margin-top: 24px;">
            <div class="panel-header">
                <h3>Register a New Vehicle</h3>
            </div>
            
            <div th:if="${flashMessage}" style="margin-bottom:14px;padding:12px 14px;border-radius:12px;background:#ecfdf5;color:#047857;font-weight:600;" th:text="${flashMessage}">Saved.</div>
            <div th:if="${flashError}" style="margin-bottom:14px;padding:12px 14px;border-radius:12px;background:#fef2f2;color:#b91c1c;font-weight:600;" th:text="${flashError}">Error.</div>
            
            <form th:action="@{/save-vehicle}" method="post">
                <div class="vehicle-form-grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
                    <div class="form-group full" style="grid-column: 1 / -1;">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Vehicle Name</label>
                        <input type="text" name="vehicleName" placeholder="e.g. Toyota RAV4" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                    </div>
                    <div class="form-group">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Brand</label>
                        <select name="brand" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                            <option value="" selected disabled hidden>Select brand</option>
                            <option>Toyota</option>
                            <option>Hyundai</option>
                            <option>Volkswagen</option>
                            <option>BMW</option>
                            <option>Mercedes</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Model</label>
                        <input type="text" name="model" placeholder="e.g. RAV4" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                    </div>
                    <div class="form-group">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Year</label>
                        <input type="number" name="year" placeholder="e.g. 2022" min="1950" max="2100" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                    </div>
                    <div class="form-group">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Plate Number</label>
                        <input type="text" name="plateNumber" placeholder="e.g. RAB 123 A" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                    </div>
                    <div class="form-group">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Fuel Type</label>
                        <select name="fuelType" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                            <option value="" selected disabled hidden>Select fuel type</option>
                            <option>Petrol</option>
                            <option>Diesel</option>
                            <option>Hybrid</option>
                            <option>Electric</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Transmission</label>
                        <select name="transmission" required style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;">
                            <option value="" selected disabled hidden>Select transmission</option>
                            <option>Automatic</option>
                            <option>Manual</option>
                        </select>
                    </div>
                    <div class="form-group full" style="grid-column: 1 / -1;">
                        <label style="display:block;margin-bottom:6px;font-size:13px;font-weight:600;color:var(--cfr-text);">Notes</label>
                        <textarea name="notes" placeholder="Add any important vehicle notes..." style="width:100%;padding:10px 12px;border:1px solid var(--cfr-border);border-radius:8px;background:var(--cfr-bg-subtle);color:var(--cfr-text);font-size:14px;min-height:80px;"></textarea>
                    </div>
                </div>
                <div class="vehicle-form-actions" style="margin-top: 24px; display: flex; justify-content: flex-end; gap: 12px;">
                    <a href="/customer-dashboard" style="padding: 10px 20px; border-radius: 8px; font-weight: 600; font-size: 14px; text-decoration: none; color: var(--cfr-text); border: 1px solid var(--cfr-border); transition: all 0.2s ease;">Cancel</a>
                    <button type="submit" style="padding: 10px 20px; border-radius: 8px; font-weight: 600; font-size: 14px; background: #0b3b92; color: white; border: none; cursor: pointer; transition: all 0.2s ease;">Save Vehicle</button>
                </div>
            </form>
        </div>
    </div>
</section>
'''

dashboard = re.sub(r'<section class="customer-dashboard-grid">.*?</section>', new_main, dashboard, flags=re.DOTALL)

with open('src/main/resources/templates/vehicle-registration.html', 'w', encoding='utf-8') as f:
    f.write(dashboard)
