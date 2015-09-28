// Morris.js Charts sample data for SB Admin template

$(function() {

    // Area Chart
    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '00 - 02',
            child: 1232,
            adult: 2324,
            senior: 1235
        },{
            period: '02 - 04',
            child: 1234,
            adult: 1733,
            senior: 1348
        },{
            period: '04 - 06',
            child: 2666,
            adult: 3123,
            senior: 2647
        }, {
            period: '06 - 08',
            child: 2778,
            ipad: 2294,
            senior: 2441
        }, {
            period: '08 - 10',
            child: 4912,
            ipad: 1969,
            senior: 2501
        }, {
            period: '10 - 12',
            child: 3767,
            adult: 3597,
            senior: 5689
        }, {
            period: '12 - 14',
            child: 6810,
            adult: 1914,
            senior: 2293
        }, {
            period: '14 - 16',
            child: 5670,
            adult: 4293,
            senior: 1881
        }, {
            period: '16 - 18',
            child: 4820,
            adult: 3795,
            senior: 1588
        }, {
            period: '18 - 20',
            child: 15073,
            adult: 5967,
            senior: 5175
        }, {
            period: '20 - 22',
            child: 10687,
            adult: 4460,
            senior: 2028
        }, {
            period: '22 - 24',
            child: 8432,
            adult: 5713,
            senior: 1791
        }],
        xkey: 'period',
        ykeys: ['child', 'adult', 'senior'],
        labels: ['child', 'adult', 'senior'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    // Donut Chart
    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Entertainment",
            value: 45.1
        }, {
            label: "Electronics",
            value: 15.9
        }, {
            label: "Travel",
            value: 13.5
        }, {
            label: "Food",
            value: 25.5
        }],
        resize: true
    });

    // Line Chart
    Morris.Line({
        // ID of the element in which to draw the chart.
        element: 'morris-line-chart',
        // Chart data records -- each entry in this array corresponds to a point on
        // the chart.
        data: [{
            d: '2012-10-01',
            visits: 802
        }, {
            d: '2012-10-02',
            visits: 783
        }, {
            d: '2012-10-03',
            visits: 820
        }, {
            d: '2012-10-04',
            visits: 839
        }, {
            d: '2012-10-05',
            visits: 792
        }, {
            d: '2012-10-06',
            visits: 859
        }, {
            d: '2012-10-07',
            visits: 790
        }, {
            d: '2012-10-08',
            visits: 1680
        }, {
            d: '2012-10-09',
            visits: 1592
        }, {
            d: '2012-10-10',
            visits: 1420
        }, {
            d: '2012-10-11',
            visits: 882
        }, {
            d: '2012-10-12',
            visits: 889
        }, {
            d: '2012-10-13',
            visits: 819
        }, {
            d: '2012-10-14',
            visits: 849
        }, {
            d: '2012-10-15',
            visits: 870
        }, {
            d: '2012-10-16',
            visits: 1063
        }, {
            d: '2012-10-17',
            visits: 1192
        }, {
            d: '2012-10-18',
            visits: 1224
        }, {
            d: '2012-10-19',
            visits: 1329
        }, {
            d: '2012-10-20',
            visits: 1329
        }, {
            d: '2012-10-21',
            visits: 1239
        }, {
            d: '2012-10-22',
            visits: 1190
        }, {
            d: '2012-10-23',
            visits: 1312
        }, {
            d: '2012-10-24',
            visits: 1293
        }, {
            d: '2012-10-25',
            visits: 1283
        }, {
            d: '2012-10-26',
            visits: 1248
        }, {
            d: '2012-10-27',
            visits: 1323
        }, {
            d: '2012-10-28',
            visits: 1390
        }, {
            d: '2012-10-29',
            visits: 1420
        }, {
            d: '2012-10-30',
            visits: 1529
        }, {
            d: '2012-10-31',
            visits: 1892
        }, ],
        // The name of the data record attribute that contains x-visitss.
        xkey: 'd',
        // A list of names of data record attributes that contain y-visitss.
        ykeys: ['visits'],
        // Labels for the ykeys -- will be displayed when you hover over the
        // chart.
        labels: ['Visits'],
        // Disables line smoothing
        smooth: false,
        resize: true
    });

    // Bar Chart
    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            device: 'Sunday',
            geekbench: 200
        }, {
            device: 'Saturday',
            geekbench: 367
        }, {
            device: 'Friday',
            geekbench: 500
        }, {
            device: 'Thursday',
            geekbench: 600
        }, {
            device: 'Wednesday',
            geekbench: 1571
        }, {
            device: 'Tuesday',
            geekbench: 900
        }, {
            device: 'Monday',
            geekbench: 700
        }],
        xkey: 'device',
        ykeys: ['geekbench'],
        labels: ['Geekbench'],
        barRatio: 0.4,
        xLabelAngle: 35,
        hideHover: 'auto',
        resize: true
    });


});
