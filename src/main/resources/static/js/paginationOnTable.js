var table = '#table';
$('#maxRows').on('change', function () {
    $('.pagination').html('');
    var maxRows = parseInt($(this).val());
    var allRows = $(table + ' tbody tr').length;
    if (allRows > maxRows) {
        var pageNumber = Math.ceil(allRows / maxRows);
        for (var i = 1; i <= pageNumber;) {
            $('.pagination').append('<button class="btn" data-page="' + i + '"><span>' + i++ + '<span class="sr-only"></span></span>').show()
        }
    } else {
        location.reload();
    }
    $('.pagination button:first-child').addClass('active');
    $('.pagination button').on('click', function () {
        var pageNumber = $(this).attr('data-page');
        var trIndex = 0;
        $('.pagination button').removeClass('active');
        $(this).addClass('active');
        $(table + ' tr:gt(0)').each(function () {
            trIndex++;
            if (trIndex > (maxRows * pageNumber) || trIndex <= ((maxRows * pageNumber) - maxRows)) {
                $(this).hide()
            } else {
                $(this).show()
            }
        })
    })
});