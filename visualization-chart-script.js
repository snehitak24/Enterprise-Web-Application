var TUTORIAL_SAVVY = {
	
/*return google visualization data*/
	getvisualizationData : function(jsonData){
	
	 var  dataArray = [],
	 
		   data = new google.visualization.DataTable();
		   
	       data.addColumn('string', 'Name');
		   data.addColumn('number','Sales');
			
			$.each(jsonData, function(i,obj){
	    	  
	    	  dataArray.push([obj.name,obj.quantity]);
	      });
	      
		  data.addRows(dataArray);
		     
	     return data;
	},
	/*return options for bar chart: these options are for various configuration of chart*/
	getOptionForBarchart : function(){
		
		  var options = {
		  			animation:{
	       					 duration: 2000,
	       					 easing: 'out'
	     			  },
		  				
			          hAxis: {
			              baselineColor: '#ccc'
			          },
			          vAxis: {
			              baselineColor: '#ccc',
			              gridlineColor: '#fff'
			          },
			
			          isStacked: true,
			          height: 400,
			          backgroundColor: '#fff',
			          colors: ["#68130E", "#c65533"],
			          fontName: 'roboto',
			          fontSize: 12,
			          legend: {
			              position: 'top',
			              alignment: 'end',
			              textStyle: {
			                  color: '#b3b8bc',
			                  fontName: 'roboto',
			                  fontSize: 12
			              }
			          }
	     		 };
		return   options;		 
		},
	/*Draws a Bar chart*/	
	drawBarChart : function (inputdata) {

		 var  barOptions = TUTORIAL_SAVVY.getOptionForBarchart(),
		
			  data = TUTORIAL_SAVVY.getvisualizationData(inputdata),
			  
			  chart = new google.visualization.ColumnChart(document.getElementById('student-bar-chart'));
			  
			  chart.draw(data, barOptions);
			  /*for redrawing the bar chart on window resize*/
		    $(window).resize(function () {
		    	
		        chart.draw(data, barOptions);
		    });
	 },
	/* Returns a custom HTML tooltip for Visualization chart*/
	 
	/*Makes ajax call to servlet and download data */
	getProductData : function(){
		
			$.ajax({
			
				url: "/csj/barchart",
				
				dataType: "JSON",
				    alert("Hi");
				success: function(data){
	
					TUTORIAL_SAVVY.drawBarChart(data);
				}
			});
	}
};	

google.load("visualization", "1", {packages:["corechart"]});
	
$(document).ready(function(){

	TUTORIAL_SAVVY.getProductData();
});
