$("document").ready(function() {
    var link = $("#link");
    link.change(function(){changePreviewImage();});
    link.blur(function(){changePreviewImage();});

    var knownColors;
    var knownColorsSorted = {0 : "transparent"};

    $("#BNTtoHamaColor").click(function(){

        var linkVal = link.val();

        $.ajax({
            url:"/process",
            type:"POST",
            data: JSON.stringify({ "link": linkVal }),
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function(data){
                console.log( data );

                knownColors = data.knownColors;
                $.each(knownColors, function(key, value){
                    knownColorsSorted[value.intValue] = value;
                });

                $("#results div").remove();
                // create preview Images
                for(var i=0; i<data.images.length; i++){
                    data.images[i].nr = i;
                    $("#results").append(
                        $("<div>").addClass("col-lg-3 col-md-3 col-xs-6").append(
                           createImage(data.images[i].imgBase64, i)
                        )).data("img",data.images[i]);
                }

                // create Result Images
                $("#detailsTables").empty();
                for(var j=0; j < data.images.length; j++){
                    var canvas = createCanvasWithTable(j, data.images[j], data.knownColors, "imgResult", 1);
                    initPanelFullSizeView(canvas);

                    var xPanels = Math.ceil(data.images[j].width / 29);
                    var yPanels = Math.ceil(data.images[j].height / 29);

                    for(var x=0; x < xPanels; x++){
                        for(var y=0; y < yPanels; y++){
                            var w = (x == xPanels-1) ? data.images[j].width % 29 : 29;
                            var h = (y == yPanels-1) ? data.images[j].height % 29 : 29;
                            var nr= j+"_"+x+"-"+y;
                            var pixelValues = [];

                            for(var xRect=0; xRect < w; xRect++){
                                for(var yRect=0; yRect < h; yRect++){
                                    //var pos = x*29 + xRect + (((y * 29) + yRect) * data.images[j].width);
                                    var pos = y*29 + yRect + (((x * 29) + xRect) * data.images[j].height);
                                    pixelValues.push(data.images[j].pixelValues[pos]);
                                }
                            }

                            var dataRect = {
                                "width": w,
                                "height": h,
                                "nr": nr,
                                "pixelValues": pixelValues
                            };
                            var canvas = createCanvasWithTable(nr, dataRect, data.knownColors, "imgResult imgResultDetails", 2)
                        }
                    }
                }

                // init visibility of result rows
                var allResultRows = $(".imgResult");
                allResultRows.addClass("hidden");
                $("#imgResult0").removeClass("hidden");

                $("#results div a.thumbnail").click(function(event){
                    event.preventDefault();
                    var nr = $(this).data("nr");
                    allResultRows.addClass("hidden");
                    $("#imgResult" + nr).removeClass("hidden");
                    return true;
                });


            }
        });
    });

    /** helper functions **/
    function createCanvasWithTable(j, imgData, knownColors, containerClass, sizeFactor) {
        var tableContainer = $('<div id="imgResult' + j + '" class="'+containerClass+'">').addClass("col-lg-12 col-md-12 cool-xs-12").append(
            '<h2>Beads Pattern Details:</h2>'
        );

        if (j > 0) {
            tableContainer.addClass("hidden");
        }

        var tableContainerRow = tableContainer.append('<div class="row">').find('div.row');
        var tableContainerCanvas = tableContainerRow.append('<div class="col-lg-6 col-md-12 canvas">').find('div.canvas');
        var tableContainerStats = tableContainerRow.append('<div class="col-lg-6 col-md-12 stats">').find('div.stats');

        var canvas = drawCanvasTo(tableContainerCanvas, imgData, j, sizeFactor);
        $(canvas).data("img", imgData);

        printResultStatsTo(tableContainerStats, imgData, j, knownColors);
        initHoverInfoBox(canvas);

        $("#detailsTables").append(tableContainer);
        return canvas;
    }

    function createImage(imgBase64, nr)
    {
        return '<a href="#" class="thumbnail" data-nr="'+nr+'"><img src="data:image/png;base64,'+imgBase64+'"></a>';
    }

    function drawCanvasTo(nodeToDraw, imgData, nr, sizeFactor)
    {
        if(sizeFactor == undefined){
            sizeFactor = 1;
        }
        var canvas = nodeToDraw.append('<canvas id="beadsTable'+nr+'" class="beadsCanvas"/>').find('canvas');
        canvas.data("sizeFactor",sizeFactor);
        var imgHeight = imgData.height * 5 * sizeFactor + 10;
        var imgWidth = imgData.width * 5 * sizeFactor + 10;
        canvas.attr("width", imgWidth);
        canvas.attr("height", imgHeight);
        var context = canvas[0].getContext('2d');

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
                context.arc(x*5*sizeFactor+5,y*5*sizeFactor+5,2*sizeFactor,0,2*Math.PI);

                var colorHex = "#"+colorRGBA[0].toString(16) +
                    colorRGBA[1].toString(16) +
                    colorRGBA[2].toString(16);
                context.lineWidth = 1 * sizeFactor * 1.5;
                context.strokeStyle= colorHex;
                context.stroke();
            }
        }

        for(var xGrid=5-3; xGrid < imgWidth ; xGrid += 29*5*sizeFactor)
        {
            for(var yGrid= 5-3; yGrid < imgHeight; yGrid += 29*5*sizeFactor)
            {
                context.strokeStyle = "#000000";
                context.strokeRect(xGrid,yGrid, 29*5*sizeFactor, 29*5*sizeFactor);
            }
        }

        return canvas;
    }

    function printResultStatsTo(nodeToPrint, imgData, nr, knownColors)
    {
        var table = nodeToPrint.append('<table id="beadsStats'+nr+'" class="beadsStats"/>').find('table');
        table.append('<tr><th>Id</th><th>Name</th><th>Color</th><th>#</th></tr>');
        var stats = countBeads(imgData, knownColors)

        $.each(knownColors, function(key, color){
            if(stats[color.intValue] == 0)
            {
                // ignore transparent
                return;
            }
            var node = '<tr><td>' + color.id + '</td><td>' + color.name + '</td><td>'+
                '<span style="border: 1px solid black; width: 60px; height: 18px; display: block; background-color: #' +
                color.red.toString(16) + color.green.toString(16) + color.blue.toString(16) +
                '"></span></td><td>' +
                stats[color.intValue] + '</td></tr>';
            table.append(node);
        });
    }

    function countBeads(imgData, knownColors){
        var result = {0 : 0};
        $.each(knownColors, function(key, color){
            result[color.intValue] = 0;
        });

        $.each(imgData.pixelValues, function(key, intVal){
            result[intVal] = result[intVal] +1;
        });

        return result;
    }

    function initHoverInfoBox(canvas){
        var infobox = $('#hoverInfoBox');
        var sizeFactor = $(canvas).data("sizeFactor");

        $(canvas).on('mousemove', function(e){
            infobox.css({
                left:  e.pageX,
                top:   e.pageY
            });
            var x = Math.floor((e.offsetX - 5 ) / (5 * sizeFactor));
            var y = Math.floor((e.offsetY - 5 ) / (5 * sizeFactor));
            var imgData = canvas.data("img");

            if(x < 0 || y < 0 || x >= imgData.width || y >= imgData.height){
                infobox.html("out of range");
                return;
            }

            var intVal = imgData.pixelValues[x*imgData.height+y];
            var color =  knownColorsSorted[intVal];
            if( color == "transparent")
            {
                infobox.html("X: "+(x+1)+" Y: "+(y+1) + " no bead / transparent");
                return;
            }
            var rgb = '#' + color.red.toString(16) + color.green.toString(16) + color.blue.toString(16);
            infobox.html("X: "+(x+1)+" Y: "+(y+1) + "id: " + color.id + " name: " + color.name + " color: "+rgb);
        });
        $(canvas).on('mouseleave',function(e){
            infobox.addClass("hidden");
        });
        $(canvas).on('mouseenter',function(e){
            infobox.removeClass("hidden");
        });
    }

    function initPanelFullSizeView(canvas) {
        $(canvas).on('click', function(e) {

            var x = Math.floor(((e.offsetX - 5 ) / 5) / 29);
            var y = Math.floor(((e.offsetY - 5 ) / 5) / 29);
            var nr = $(e.currentTarget).data("img").nr;

            var allResultDetailsRows = $(".imgResultDetails");
            var targetResultDetails = $("#imgResult"+nr+"_"+x+"-"+y);
            var modalBody = $("#showPatternDetails div.modal-body");

            // clear modal
            modalBody.empty();

            // copy into modal window
            targetResultDetails.clone(true, true).appendTo( modalBody);
            modalBody.find(".imgResultDetails").removeClass("hidden");
            // copy canvas
            var destCtx = $(modalBody.find('canvas'))[0].getContext('2d');
            destCtx.drawImage(targetResultDetails.find("canvas")[0], 0, 0);
            // remove bootstrap foo classes
            modalBody.find(".imgResult").removeClass("col-lg-12 col-md-12 cool-xs-12");

            // show modal
            $('#showPatternDetails').modal('show')

        });
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