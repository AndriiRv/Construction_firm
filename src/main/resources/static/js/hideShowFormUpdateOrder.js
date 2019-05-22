function hideShowForm() {
    var firstForm = document.getElementById("formUpdateWorker");
    var secondForm = document.getElementById("formUpdateMaterial");
    var thirdForm = document.getElementById("formUpdateInstrument");
    var button = document.getElementById("hideShowButton");
    if (firstForm.style.display === "none" && secondForm.style.display === "none" && thirdForm.style.display === "none") {
        firstForm.style.display = "block";
        secondForm.style.display = "block";
        thirdForm.style.display = "block";
        button.innerHTML = 'Hide form Update';
    } else {
        firstForm.style.display = "none";
        secondForm.style.display = "none";
        thirdForm.style.display = "none";
        button.innerHTML = 'Show form Update';
    }
}