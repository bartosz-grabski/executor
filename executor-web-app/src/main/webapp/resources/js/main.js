function initDatePickers(settings) {
    // $('.datepicker').each(function() {
    //
    // $(this).datepicker({
    // 
    // });
    //
    // });

    var options = {
        format : "dd-mm-yyyy",
        autoclose : true,
        todayHighlight : true,
        clearBtn : true,
        onChangeEvent : function(e) {
        }
    };

    $.extend(options, settings);

    $('.datepicker').each(function() {
        $(this).datepicker(options).on('changeDate', function(e) {
            options.onChangeEvent(e);
        });
    });
}

function initDateTimePickers(settings) {

    var options = {
        format : "dd/MM/yyyy hh:mm",
        maskInput : false,
        pickSeconds : false
    };

    $.extend(options, settings);

    $('.datetimepicker').datetimepicker(options);

}