$(document).ready(function () {
    $("#idOrder").on("change", function () {
        document.getElementById('imgSelect').style.display = 'block';
        if ($(this).val() === "9 storey building") {
            $("#imgSelect").attr("src", "../images/9Storeybuild.jpg");
        } else if ($(this).val() === "5 storey building") {
            $("#imgSelect").attr("src", "../images/5Storeybuild.jpg");
        } else if ($(this).val() === "Shopping mall") {
            $("#imgSelect").attr("src", "../images/shoppingMall.jpg");
        } else if ($(this).val() === "Hospital") {
            $("#imgSelect").attr("src", "../images/hospital.png");
        } else if ($(this).val() === "12 storey building") {
            $("#imgSelect").attr("src", "../images/12Storeybuild.jpg");
        } else if ($(this).val() === "Two-storey villa") {
            $("#imgSelect").attr("src", "../images/privateHome2.jpg");
        }
    });
});
$('.mb-4').on('click', function () {
    $('.overlay img').attr('src', $(this).attr('src'));
    $('.overlay').show();
});
$('.overlay').on('click', function () {
    $('.overlay').hide();
});