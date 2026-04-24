document.addEventListener("click", function (event) {
    var trigger = event.target.closest("[data-picker-target]");
    if (!trigger) {
        return;
    }

    var targetId = trigger.dataset.pickerTarget;
    if (!targetId) {
        return;
    }

    var input = document.getElementById(targetId);
    if (!input) {
        return;
    }

    event.preventDefault();

    if (typeof input.showPicker === "function") {
        input.showPicker();
        return;
    }

    input.focus();
    input.click();
});
