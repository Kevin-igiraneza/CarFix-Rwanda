(function () {
    const storageKey = "carfix.dashboardSidebarCollapsed";

    function applyState(shell, collapsed) {
        shell.classList.toggle("sidebar-collapsed", collapsed);

        const toggle = shell.querySelector(".dashboard-sidebar-toggle");
        if (toggle) {
            toggle.setAttribute("aria-expanded", String(!collapsed));
            toggle.setAttribute("aria-label", collapsed ? "Expand sidebar" : "Collapse sidebar");
        }
    }

    function shouldCollapse() {
        try {
            return window.localStorage.getItem(storageKey) === "true";
        } catch (e) {
            return false;
        }
    }

    function saveState(collapsed) {
        try {
            window.localStorage.setItem(storageKey, String(collapsed));
        } catch (e) {
            // Ignore storage failures; the sidebar still works for the session.
        }
    }

    function initShell(shell) {
        const toggle = shell.querySelector(".dashboard-sidebar-toggle");
        if (!toggle) {
            return;
        }

        shell.querySelectorAll(".dashboard-menu-link").forEach(function (link) {
            const label = link.querySelector("span:last-child");
            if (label && !link.getAttribute("title")) {
                link.setAttribute("title", label.textContent.trim());
            }
        });

        const logoutButton = shell.querySelector(".dashboard-logout-btn");
        if (logoutButton && !logoutButton.getAttribute("title")) {
            logoutButton.setAttribute("title", "Log out");
        }

        const syncForViewport = function () {
            if (window.innerWidth <= 1100) {
                shell.classList.remove("sidebar-collapsed");
                toggle.setAttribute("aria-expanded", "true");
                toggle.setAttribute("aria-label", "Collapse sidebar");
                return;
            }
            applyState(shell, shouldCollapse());
        };

        syncForViewport();

        toggle.addEventListener("click", function () {
            if (window.innerWidth <= 1100) {
                return;
            }

            const collapsed = !shell.classList.contains("sidebar-collapsed");
            applyState(shell, collapsed);
            saveState(collapsed);
        });

        window.addEventListener("resize", syncForViewport);
    }

    document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll(".dashboard-shell").forEach(initShell);
    });
})();
