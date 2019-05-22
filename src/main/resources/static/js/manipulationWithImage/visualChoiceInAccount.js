$(document).ready(function () {
    $('.classTitle').on('click', function () {
        document.getElementById('preview').style.display = 'block';
        if ($(this).text() === "9 storey building") {
            $("#preview").attr("src", "../images/9Storeybuild.jpg");
        } else if ($(this).text() === "5 storey building") {
            $("#preview").attr("src", "../images/5Storeybuild.jpg");
        } else if ($(this).text() === "Shopping mall") {
            $("#preview").attr("src", "../images/shoppingMall.jpg");
        } else if ($(this).text() === "Hospital") {
            $("#preview").attr("src", "../images/hospital.png");
        } else if ($(this).text() === "12 storey building") {
            $("#preview").attr("src", "../images/12Storeybuild.jpg");
        } else if ($(this).text() === "Two-storey villa") {
            $("#preview").attr("src", "../images/privateHome2.jpg");
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