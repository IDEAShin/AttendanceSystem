$(
    //自动获取列表
    function () {
        initEmployeeList();
        initSelect();
    }
);
//出勤列表加载
function initEmployeeList() {
    // 加载表格
    $("#table").datagrid({
        title: "出勤列表",
        iconCls: "icon-left02",
        url: 'json/attendanceStatiDeptComp.json',
        fitColumns: true,
        striped: true,
        pagination: true,
        pageSize: 10,
        width: '100%',
        rownumbers: true,
        pageList: [10, 20],
        pageNumber: 1,
        nowrap: true,
        height: 'auto',
        sortName: 'attendanceID',
        checkOnSelect: false,
        sortOrder: 'asc',
        toolbar: '#tabelBut',
        columns:[[
            {
                checkbox:true,
                field:'no',
                width:100,
                align:'center'
            },
            {
                field:'attendanceID',
                title:'编号',
                width:100,
                align:'center'
            },
            {
                field:'departmentOne',
                title:'一级部门',
                width:100,
                align:'center'
            },
            {
                field:'departmentTow',
                title:'二级部门',
                width:100,
                align:'center'
            },
            {
                field:'cardNumber',
                title:'员工卡号',
                width:100,
                align:'center'
            },
            {
                field:'employeeName',
                title:'姓名',
                width:100,
                align:'center'
            },
            {
                field:'attendanceType1',
                title:'迟到',
                width:100,
                align:'center'
            },
            {
                field:'attendanceType2',
                title:'病假',
                width:100,
                align:'center'
            }, {
                field:'attendanceType3',
                title:'事假',
                width:100,
                align:'center'
            }, {
                field:'attendanceType4',
                title:'丧家',
                width:100,
                align:'center'
            }, {
                field:'attendanceType5',
                title:'婚嫁',
                width:100,
                align:'center'
            }, {
                field:'attendanceType6',
                title:'矿工',
                width:100,
                align:'center'
            }, {
                field:'attendanceDayNow',
                title:'出勤天数',
                width:100,
                align:'center'
            }, {
                field:'attendanceDay',
                title:'应出勤天数',
                width:100,
                align:'center'
            }
        ]]
    })
}

//编辑出勤
function searchOne(rowEdit) {
}

//加载select
function initSelect() {
    var data1, data2;
    data1 = [];
    data2 = [];
    for (var i = 2009; i < 2020; i++) {
        data1.push({ "text": i, "id": i });
    }

    for (var j = 0; j < 12; j++) {
        data2.push({ "text": j, "id": j });
    }
    $("#selectY").combobox("loadData", data1);
    $("#selectM").combobox("loadData", data2);
}
