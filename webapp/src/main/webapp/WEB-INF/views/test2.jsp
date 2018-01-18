<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/resources/styles.jsp" %>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <style>

        label{
            border: none;
            color: #4CAF50;
            padding: 18px;
            text-align: center;
            text-decoration: none;
            font-size: 18px;
            margin: 0px 20px;
            z-index: 9999;


        }

        button {
            position: absolute;
            background-color: #4CAF50; /* Green */
            border: none;
            color: white;
            padding: 7px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 0px 215px;
            cursor: pointer;
            border-radius: 6px;
        }

    </style>
</head>
<body>
<%@include file="/resources/bar.jsp" %>

<div class="container" name = "toggleButton" onclick="resize(this)">
    <div class="bar1"></div>
    <div class="bar2"></div>
    <div class="bar3"></div>
</div>
<div id="map" style="width:100%;left:0%;height: 89%;z-index: 4000;position:absolute"></div>


</br></br></br>

<label>Device select</label>
<div class="styled-select slate">
    <select id = "deviceSelect" onchange=onChange()>
        <option>none</option>
    </select>
</div>

</br>

<label> Ammount of points</label>
<div class="styled-select slate">
    <select id = "ammountOfPoints" onchange="changeAmmountOfPoints()">
        <option>5</option>
        <option>10</option>
        <option>25</option>
        <option>50</option>
        <option>100</option>
    </select>
</div>

</br>

<label> Track current point</label>
<div class="styled-select slate">
    <select id = "trackLast" onchange=changeTrackLast()>
        <option>true</option>
        <option>false</option>
    </select>
</div>

<label>Add new device</label>
<div>
    <textarea id= "textArea" cols="24" style= "position: absolute;margin: 0px 20px; height: 36px;"></textarea>
    <button onclick=addDevice()>Add</button>
</div>


<script>

    var request = new XMLHttpRequest();
    var map;
    var polyline;
    var last_date = "none";
    var device_id = 1231;
    var points_limit = 5;
    var markers = [];
    var interval = 0;
    var trackLastPoint = 'true';
    var last_position;


    function addDevice() {
        var textarea = document.getElementById("textArea");
        var responsebody;
        if(textarea.value.length < 5)return;
        request.onreadystatechange = function (){responsebody = request.responseText;}
        request.open("POST", "/add_device/"+ textarea.value, false);
        request.send();
        var added = JSON.parse(request.responseText).added;
        if(added == true){
            var select = document.getElementById("deviceSelect");
            var option = document.createElement("option");
            option.text = textarea.value;
            select.add(option);
        }
        textarea.val = "";
    }

    function changeTrackLast() {
        var select = document.getElementById("trackLast");
        trackLastPoint = select.options[select.selectedIndex].value;
    }

    function changeAmmountOfPoints() {

        var select = document.getElementById("ammountOfPoints");
        var temp_ammount = select.options[select.selectedIndex].value;
        if(markers.length == points_limit || temp_ammount < markers.length){
            clear();
            last_date = "none";
        }

        points_limit = temp_ammount;


    }

    function resize(x) {
        x.classList.toggle("change");
        var x = document.getElementById("map");
        if(x.style.width == '100%'){
            x.style.width = '85%';
            x.style.left = '15%';
        }
        else {
            x.style.width = '100%';
            x.style.left = '0%';

        }

    }

    function initialize() {
        myMap();
        fillDeviceList();
    }
    
    function onChange() {

        var deviceSelect = document.getElementById("deviceSelect");
        device_id = deviceSelect.options[deviceSelect.selectedIndex].value;
        if(interval != 0){
            clear();
            clearInterval(interval);
            last_date = "none"
        }
        if(device_id != "none"){
            refresh();
            interval = setInterval(refresh, 5000);
        }
    }

    function clear() {
        var temp = points_limit;
        points_limit = 0;
        removeTillRight();
        points_limit  = temp;
    }

    function fillDeviceList() {
        request.onreadystatechange = function (){jsontext = request.responseText;}
        request.open("GET", "/devices/", false);
        request.send();
        device_list = JSON.parse(request.responseText).device_list;
        var select = document.getElementById("deviceSelect");
        device_list.forEach(function (t) {
            var opt = document.createElement("option");
            opt.value = t;
            opt.innerHTML = t;
            select.appendChild(opt);
            }
        );


    }

    function myMap() {
        var mapCanvas = document.getElementById("map");
        var arr = [];
        polyline = new google.maps.Polyline({
            path: arr,
            strokeColor: "#0000FF",
            strokeOpacity: 0.8,
            strokeWeight: 5
        });

        var mapOptions = {
            center: new google.maps.LatLng(50,5),
            zoom: 15,
            mapTypeControl: false,
            scaleControl: false,
            streetViewControl: false,
            rotateControl: false};
        map = new google.maps.Map(mapCanvas,mapOptions);
        polyline.setMap(map);


    }

    function addMarker(position, date) {
        var marker = new google.maps.Marker({
            position: position,
            title:date.slice(0, 19)
        });
        markers.push(marker);
        marker.setMap(map);
    }

    function removeTillRight() {
        while(markers.length > points_limit){
            markers[0].setMap(null);
            markers.shift();
            polyline.getPath().removeAt(0);
        }


    }

    function addPoint(point) {
        var json = JSON.parse(point);
        var postion = new google.maps.LatLng(json.x,json.y);
        last_date =json.date;
        polyline.getPath().push(postion);
        addMarker(postion, last_date);
        last_position = postion;
    }

    function refresh() {
        request.onreadystatechange = function (){jsontext = request.responseText;}
        request.open("GET", "/get_locations/"+ device_id+"/"+last_date+"/"+points_limit, false);
        request.send();
        json = JSON.parse(request.responseText).locations.reverse();
        json.forEach(function (t) { addPoint(t);});
        removeTillRight();

        if(json.length > 0 && trackLastPoint == 'true'){
            map.setCenter(last_position);
        }

    }

</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCBx1X3Fd26g7pwqfng7zqK9qleIX99dJU&callback=initialize"></script>


</body>
</html>
