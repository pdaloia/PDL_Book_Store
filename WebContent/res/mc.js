function newCustomer() {
	var checkBox = document.getElementById("newCheckBox");
	var text = document.getElementById("newCustomeOrder");
	if (checkBox.checked == true) {
		text.style.display = "block";
	} else {
		text.style.display = "none";
	}
}