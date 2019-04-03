/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */






// shopping cart module starts here

function postData(params, callback) {
    xhr = new XMLHttpRequest();

    xhr.open('POST', window.location.href);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {

        if (xhr.status == 200 && xhr.responseText) {
            callback(xhr.status, xhr.responseText);
        } else if (xhr.status !== 200) {

            callback(xhr.status, "Error! Unable to process request");
        }
    };
    xhr.send(encodeURI(params));


}


function removeItem(bid, username) {
    if (confirm('Are you sure ?')) {

        postData("bid=" + bid + "&username=" + username + "&action=remove", function (status, text) {
            if (status === 200) {
                window.location = window.location;
            } else {
                alert(text);
            }

        });

    }
}

function updateChanges(){
    var query = "";
    var quantities = document.querySelectorAll(".itemQuantity");
    for(var i=0;i<quantities.length;i++){
        
        var elm = quantities[i];
        var realVal = elm.value;
        if(!realVal ||  realVal<1){
            alert('Invalid quantiy');
            return ;
        }s
        var val = elm.getAttribute('data-val');
        query+="item="+realVal+","+val+"&";
        
        
    }
    query+="action=update";
    postData(query,function(status,text){
        if(status===200){
            window.location=window.location;
            
        }else{
            alert(text);
            
        }
        
    });
    
    
}

function addItemToCart(bid, title,price){
    
    var params = "bid="+bid+"&title="+title+"&price="+price;
    var xhr = new XMLHttpRequest();

    xhr.open('POST', "ShoppingCart");
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {

        if (xhr.status == 200 && xhr.responseText) {
            
            
            var count = parseInt(document.getElementById("cartItemCount").innerHTML)+1; 
            if(isNaN(count)){
                count=1;
            }
            if(count>99){
                count="99+";
            }
             document.getElementById("cartItemCount").innerHTML  = count;
            
            
            
        } else if (xhr.status !== 200) {

            alert("Error! Unable to process request");
        }
    };
    xhr.send(encodeURI(params));
    alert("Successfully added!");
    
    
}

// shopping cart module ends here


// payment module starts here
 
function displayOnClick(checkboxId,textId) {
	var checkBox = document.getElementById(checkboxId);
	var text = document.getElementById(textId);
	if (checkBox.checked == true) {
		text.style.display = "block";
	} else {
		text.style.display = "none";
	}
}


//payment module ends here
