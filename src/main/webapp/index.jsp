<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<h2>Watermark Service</h2>
<h2>Put your infos and click submit to watermark documents </h2>
<div class="content">
    <p class="title"><b>Title :</b><br>
        <input type="text" size="40">
    </p>
    <p class="author"><b>Author :</b><br>
        <input type="text" size="40">
    </p>
    <p class="topic"><b>Topic :</b><br>
        <input type="radio" name="browser" value="Science"> Science <Br>
        <input type="radio" name="browser" value="Business"> Business<Br>
        <input type="radio" name="browser" value="Media"> Media<Br>
        <input type="radio" name="browser" value="No"> No Topic<Br>

    </p>
    <p>
        <input type="button" class="submit" value="Submit">
    </p>

</div>
<div id="results" style="display: none;">
    <p>Watermark : </p><br>
    <p class="watermark"><%=request.getAttribute("watermark")%></p>
</div>

<script type="text/javascript" >
    $(document).ready(function(){
        $('.submit').on('click', function(e){
            ajaxCallPOST();
        });
    });

    function ajaxCallPOST(){
        var title = $('.content').find('.title').find('input').val();
        var author = $('.content').find('.author').find('input').val();
        var genre = $('input[name=browser]:checked').val();

        if (title != '' && author != ''){
            $.post( "watermarkService", { title: title, author: author, genre: genre})
                    .done(function( data, statusText, xhr ) {
                        if (xhr.status == 202){
                            ajaxCallGET(data);
                        }

                    });
        }
    }

    function ajaxCallGET(uuid){
        $.get( "watermarkService", { uuid: uuid})
                .done(function( data, statusText, xhr ) {
                    $("p.watermark").text(data);
                    $("#results").show();
                    $('.content').find('.title').find('input').val('');
                    $('.content').find('.author').
                            find('input').val('');
                    $('input[name=browser]:checked').prop( "checked", false );
                });

    }
</script>

</body>

</html>
