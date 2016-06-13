$("document").ready(function() {
    var link = $("#link");
    link.change(function(){changePreviewImage();});
    link.blur(function(){changePreviewImage();});

    var linkVal = link.val();

    $("#BNTtoHamaColor").click(function(){

        $.ajax({
            url:"/process",
            type:"POST",
            data: JSON.stringify({ "link": linkVal }),
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function(data){
                console.log( data );

                $("#results div").remove();
                // create preview Images
                for(var i=0; i<data.images.length; i++){
                    $("#results").append(
                        $("<div>").addClass("col-md-6 cool-xs-12").append(
                           createImage(data.images[i].imgBase64)
                        )).data("img",data.images[i]);
                }

                // create Result Images
                for(var j=0; j<data.images.length; j++){
                    var tableContainer = $("<div>").addClass("col-md-6 cool-xs-12").append(
                        '<h2>Beads Pattern Details:</h2>'
                    );
                    $("#detailsTables").append(tableContainer);

                    tableContainer.data("img",data.images[j]);
                    drawCanvasTo(tableContainer, data.images[j], j);
                }

                $("#results div a.thumbnail").click(function(){

                });
            }
        });
    });

    function createImage(imgBase64)
    {
        return '<a href="#" class="thumbnail"><img src="data:image/png;base64,'+imgBase64+'"></a>';
    }

    function drawCanvasTo(nodeToDraw, imgData, nr)
    {
        var canvas = nodeToDraw.append('<canvas id="beadsTable'+nr+'" class="beadsCanvas"/>').find('canvas');
        canvas.attr("width", imgData.width * 5);
        canvas.attr("height", imgData.height * 5);
        var context = canvas[0].getContext('2d');

        context.strokeStyle = "#ff0000";
        context.fillStyle="#FFFFFF";
        context.fillRect(0,0,imgData.width * 5, imgData.height * 5);
        for(var y=0; y < imgData.height; y++){
            for(var x=0; x < imgData.width; x++){

                var strokeColor = imgData.pixelValues[x*imgData.height+y];
                var colorRGBA = int2rgba(strokeColor);
                if(colorRGBA[3] < 1)
                {
                    continue;
                }

                context.beginPath();
                context.arc(x*5,y*5,2,0,2*Math.PI);

                var colorHex = "#"+colorRGBA[0].toString(16) +
                    colorRGBA[1].toString(16) +
                    colorRGBA[2].toString(16);

                context.strokeStyle= colorHex;
                context.stroke();
            }
        }
    };





});


function changePreviewImage() {
    $("#linkPreviewPicture").attr("src", $("#link").val());
}


function int2rgba(int) {
    var a = (int >> 24) & 0xFF;
    var r = (int >> 16) & 0xFF;
    var g = (int >> 8) & 0xFF;
    var b = int & 0xFF;
    return [r,g,b,a];
}