$("document").ready(function() {
    var link = $("#link");
    link.change(function(){changePreviewImage();});
    link.blur(function(){changePreviewImage();});

    var linkVal = link.val();

    var context = document.getElementById("resultPicture").getContext('2d');
    var imageObj = new Image();
    imageObj.src = "http://localhost:8080/no-image.png";

    imageObj.onload = function() {
        context.drawImage(imageObj, 0,0, 512, 512);
    };

    $("#BNTtoHamaColor").click(function(){

        $.ajax({
            url:"/process",
            type:"POST",
            data: JSON.stringify({ "link": linkVal }),
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function(data){
                console.log( data );

                var context = document.getElementById("resultPicture").getContext('2d');
                var imageObj = new Image();
                imageObj.src = "data:image/png;base64," + data.imgBase64;

                imageObj.onload = function() {
                    context.fillStyle="#FFFFFF";
                    context.fillRect(0,0,1000,1000);
                    context.drawImage(imageObj, 0,0, data.width, data.height);
                };
            }
        });
    });

});


function changePreviewImage() {
    $("#previewPicture").attr("src", $("#link").val());
}


function int2rgba(int) {
    var a = (int >> 24) & 0xFF;
    var r = (int >> 16) & 0xFF;
    var g = (int >> 8) & 0xFF;
    var b = int & 0xFF;
    return [r,g,b,a];
}