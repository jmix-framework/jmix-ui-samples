io_jmix_sampler_screen_ui_components_javascript_component_TimePicker = function () {
    let connector = this;
    let element = connector.getElement();
    element.innerHTML = "<input type=\"text\" name=\"timepicker\" class=\"timepicker\"/>";

    let timepicker;

    // Handle changes from the server-side
    this.onStateChange = function () {
        let data = this.getState().data;

        let options = {
            now: data.now,
            twentyFour: data.twentyFour,
            showSeconds: data.showSeconds,
            beforeShow: function () {
                connector.onBeforeShow();
            },
            show: function () {
                connector.onShow();
            }
        };

        timepicker = $('.timepicker').wickedpicker(options);

        // Define a function that can be called from the server side
        connector.showValue = function () {
            alert(timepicker.wickedpicker('time'));
        };
    };
};
