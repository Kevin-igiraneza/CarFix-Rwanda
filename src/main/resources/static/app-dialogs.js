document.addEventListener("DOMContentLoaded", function () {
    var activeDialog = null;

    document.querySelectorAll(".app-modal-backdrop").forEach(function (node) {
        node.remove();
    });

    function buildDialog() {
        var backdrop = document.createElement("div");
        backdrop.className = "app-modal-backdrop";
        backdrop.hidden = true;
        backdrop.innerHTML = [
            '<div class="app-modal" role="dialog" aria-modal="true" aria-labelledby="appModalTitle">',
            '  <div class="app-modal-kicker">CarFix Rwanda</div>',
            '  <h3 id="appModalTitle" class="app-modal-title"></h3>',
            '  <p class="app-modal-message"></p>',
            '  <label class="app-modal-field" hidden>',
            '    <span class="app-modal-field-label"></span>',
            '    <textarea class="app-modal-textarea" rows="4" maxlength="500"></textarea>',
            '  </label>',
            '  <p class="app-modal-error" hidden></p>',
            '  <div class="app-modal-actions">',
            '    <button type="button" class="app-modal-btn app-modal-btn--ghost" data-modal-cancel>Cancel</button>',
            '    <button type="button" class="app-modal-btn" data-modal-confirm>Confirm</button>',
            '  </div>',
            '</div>'
        ].join("");
        document.body.appendChild(backdrop);
        return backdrop;
    }

    var dialog = buildDialog();
    var titleEl = dialog.querySelector(".app-modal-title");
    var messageEl = dialog.querySelector(".app-modal-message");
    var fieldWrap = dialog.querySelector(".app-modal-field");
    var fieldLabel = dialog.querySelector(".app-modal-field-label");
    var fieldInput = dialog.querySelector(".app-modal-textarea");
    var errorEl = dialog.querySelector(".app-modal-error");
    var cancelBtn = dialog.querySelector("[data-modal-cancel]");
    var confirmBtn = dialog.querySelector("[data-modal-confirm]");

    function readAttribute(trigger, name, fallback) {
        var value = trigger.getAttribute(name);
        if (typeof value === "string" && value.trim()) {
            return value.trim();
        }
        return fallback;
    }

    function closeDialog() {
        activeDialog = null;
        dialog.hidden = true;
        dialog.classList.remove("is-open");
        document.body.classList.remove("modal-open");
        titleEl.textContent = "";
        messageEl.textContent = "";
        fieldWrap.hidden = true;
        fieldLabel.textContent = "";
        fieldInput.value = "";
        fieldInput.placeholder = "";
        fieldInput.required = false;
        errorEl.hidden = true;
        errorEl.textContent = "";
    }

    function submitFromDialog() {
        if (!activeDialog) {
            return;
        }

        var promptTarget = activeDialog.trigger.dataset.confirmPromptTarget;
        if (promptTarget) {
            var value = fieldInput.value.trim();
            if (activeDialog.promptRequired && !value) {
                errorEl.textContent = "This message is required.";
                errorEl.hidden = false;
                fieldInput.focus();
                return;
            }
            if (activeDialog.form) {
                var hiddenField = activeDialog.form.querySelector('[name="' + promptTarget + '"]');
                if (hiddenField) {
                    hiddenField.value = value;
                }
            }
        }

        var trigger = activeDialog.trigger;
        var form = activeDialog.form;
        closeDialog();

        if (form) {
            if (typeof form.requestSubmit === "function") {
                form.requestSubmit(trigger);
                return;
            }
            if (trigger.name) {
                var hiddenSubmitter = document.createElement("input");
                hiddenSubmitter.type = "hidden";
                hiddenSubmitter.name = trigger.name;
                hiddenSubmitter.value = trigger.value;
                form.appendChild(hiddenSubmitter);
            }
            form.submit();
            return;
        }

        if (trigger.tagName === "A" && trigger.href) {
            window.location.href = trigger.href;
        }
    }

    function openDialog(trigger) {
        activeDialog = {
            trigger: trigger,
            form: trigger.closest("form"),
            promptRequired: readAttribute(trigger, "data-confirm-prompt-required", "false") === "true"
        };

        titleEl.textContent = readAttribute(trigger, "data-confirm-title", trigger.textContent.trim() || "Please confirm");
        messageEl.textContent = readAttribute(trigger, "data-confirm", "Are you sure you want to continue?");
        confirmBtn.textContent = readAttribute(trigger, "data-confirm-accept", "Confirm");
        cancelBtn.textContent = readAttribute(trigger, "data-confirm-cancel", "Cancel");

        if (trigger.hasAttribute("data-confirm-prompt-target")) {
            fieldWrap.hidden = false;
            fieldLabel.textContent = readAttribute(trigger, "data-confirm-prompt-label", "Message");
            fieldInput.placeholder = readAttribute(trigger, "data-confirm-prompt-placeholder", "");
            fieldInput.required = activeDialog.promptRequired;
            window.setTimeout(function () {
                fieldInput.focus();
            }, 0);
        } else {
            fieldWrap.hidden = true;
            window.setTimeout(function () {
                confirmBtn.focus();
            }, 0);
        }

        errorEl.hidden = true;
        errorEl.textContent = "";
        dialog.hidden = false;
        dialog.classList.add("is-open");
        document.body.classList.add("modal-open");
    }

    document.addEventListener("click", function (event) {
        var trigger = event.target.closest("[data-confirm]");
        if (!trigger || trigger.disabled) {
            return;
        }
        event.preventDefault();
        openDialog(trigger);
    });

    cancelBtn.addEventListener("click", closeDialog);
    confirmBtn.addEventListener("click", submitFromDialog);

    dialog.addEventListener("click", function (event) {
        if (event.target === dialog) {
            closeDialog();
        }
    });

    document.addEventListener("keydown", function (event) {
        if (event.key === "Escape" && activeDialog) {
            closeDialog();
        }
    });

    closeDialog();
});
