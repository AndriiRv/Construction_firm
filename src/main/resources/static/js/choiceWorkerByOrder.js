$(document).ready(function () {
    $('.select:first').change(function () {
        var s = $('.select:first option:selected').text();
        var others = $(':not(.select:first) > option');
        others.each(function () {
            if (this.text === s) {
                $(this).css('color', 'red');
                $(this).prop('selected', true);
            } else {
                $(this).css('color', 'black');
            }
        });
    });
});