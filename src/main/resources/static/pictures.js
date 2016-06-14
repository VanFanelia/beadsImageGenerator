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
                    data.images[i].nr = i;
                    $("#results").append(
                        $("<div>").addClass("col-lg-3 col-md-6 cool-xs-12").append(
                           createImage(data.images[i].imgBase64, i)
                        )).data("img",data.images[i]);
                }

                // create Result Images
                $("#detailsTables").empty();
                for(var j=0; j < data.images.length; j++){
                    var tableContainer = $('<div id="imgResult'+j+'" class="imgResult">').addClass("col-lg-12 col-md-12 cool-xs-12").append(
                        '<h2>Beads Pattern Details:</h2>'
                    );

                    if(j > 0){
                        tableContainer.addClass("hidden");
                    }

                    tableContainer.data("img",data.images[j]);

                    var tableContainerRow = tableContainer.append('<div class="row">').find('div.row');
                    var tableContainerCanvas = tableContainerRow.append('<div class="col-lg-6 col-md-12 canvas">').find('div.canvas');
                    var tableContainerStats = tableContainerRow.append('<div class="col-lg-6 col-md-12 stats">').find('div.stats');

                    drawCanvasTo(tableContainerCanvas, data.images[j], j);
                    printResultStatsTo(tableContainerStats, data.images[j], j );




                    $("#detailsTables").append(tableContainer);
                }

                $("#results div a.thumbnail").click(function(event){
                    event.preventDefault();
                    var nr = $(this).data("nr");
                    console.log(nr);
                    $(".imgResult").addClass("hidden");
                    $("#imgResult" + nr).removeClass("hidden");
                    return false;
                });
            }
        });
    });

    function createImage(imgBase64, nr)
    {
        return '<a href="#" class="thumbnail" data-nr="'+nr+'"><img src="data:image/png;base64,'+imgBase64+'"></a>';
    }

    function drawCanvasTo(nodeToDraw, imgData, nr)
    {
        var canvas = nodeToDraw.append('<canvas id="beadsTable'+nr+'" class="beadsCanvas"/>').find('canvas');
        var imgHeight = imgData.height * 5 + 10;
        var imgWidth = imgData.width * 5 + 10;
        canvas.attr("width", imgWidth);
        canvas.attr("height", imgHeight);
        var context = canvas[0].getContext('2d');

        context.strokeStyle = "#ff0000";
        context.fillStyle="#FFFFFF";
        context.fillRect(0,0,imgWidth, imgHeight);
        for(var y=0; y < imgData.height; y++){
            for(var x=0; x < imgData.width; x++){

                var strokeColor = imgData.pixelValues[x*imgData.height+y];
                var colorRGBA = int2rgba(strokeColor);
                if(colorRGBA[3] < 1)
                {
                    continue;
                }

                context.beginPath();
                context.arc(x*5+5,y*5+5,2,0,2*Math.PI);

                var colorHex = "#"+colorRGBA[0].toString(16) +
                    colorRGBA[1].toString(16) +
                    colorRGBA[2].toString(16);

                context.strokeStyle= colorHex;
                context.stroke();
            }
        }
    };

    function printResultStatsTo(nodeToPrint, imgData, nr)
    {
        var table = nodeToPrint.append('<table id="beadsStats'+nr+'" class="beadsStats"/>').find('table');
        console.log(table);
    }



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