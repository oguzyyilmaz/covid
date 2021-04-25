import React, {Component} from 'react';
import * as ReactBootStrap from 'react-bootstrap';
import CanvasJSReact from '../../../assets/canvasjs.react';
import CovidDataService from "../../data/covid-services/CovidDataService";
import DatePicker from 'react-datepicker';

import "react-datepicker/dist/react-datepicker.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import Select from 'react-select'

var CanvasJSChart = CanvasJSReact.CanvasJSChart;
var dps = [
    {y: 0, label: "VAKA SAYISI"},
    {y: 0, label: "iYİLEŞEN HASTA SAYISI"},
    {y: 0, label: "VEFAT EDEN HASTA SAYISI"}
];
const cityOptionList = [
    {value: 'Ankara', label: 'Ankara'}
];
var updateInterval = 500;

class ReportCovid extends Component {
    constructor(props) {
        super(props);
        this.updateChart = this.updateChart.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleCheckBoxChange = this.handleCheckBoxChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);

        this.state = {
            covidData: {},
            selectedCity: "-1",
            reportDate: new Date(),
            isCumulative: false
        };
    }

    componentDidMount() {
        setInterval(this.updateChart, updateInterval);

        CovidDataService.getCityList()
            .then(response => {
                this.setState({cityList: response.data})
                cityOptionList[0] = {value: '-1', label: 'TÜM İLLER'}
                for (var i = 0; i < response.data.length; i++) {
                    cityOptionList[i + 1] = {value: response.data[i].value, label: response.data[i].label}
                }
            })
            .catch(e => {
                console.log(e);
            });
    }

    updateChart() {
        var requestData = {
            reportDate: this.state.reportDate,
            isCumulative: this.state.isCumulative,
            cityCode: this.state.selectedCity.value
        };
        CovidDataService.getDailyCovidData(requestData)
            .then(response => {
                this.setState({covidData: response.data})
            })
            .catch(e => {
                console.log(e);
            });

        var dps = this.chart.options.data[0].dataPoints

        dps[0] = {label: "Vaka Sayısı", y: this.state.covidData.totalCovidCase, color: "#e40000"};
        dps[1] = {label: "İyileşen Hasta Sayısı", y: this.state.covidData.totalCovidDischarge, color: "#88df86"};
        dps[2] = {label: "Vefat Eden Hasta Sayısı", y: this.state.covidData.totalCovidDeath, color: "#4B1933"};

        this.chart.options.data[0].dataPoints = dps;
        this.chart.options.title.text = "TÜRKİYE COVID-19 HASTA TABLOSU";
        this.chart.render();
    }

    handleDateChange(date) {
        this.setState({reportDate: date});
    }

    handleCheckBoxChange() {
        this.setState({isCumulative: this.state.isCumulative ? false : true})
    }

    handleSelectChange(selection) {
        this.setState({selectedCity: selection})
    }

    render() {
        const options = {
            theme: "dark2",
            title: {
                text: "TÜRKİYE COVID-19 HASTA TABLOSU"
            },
            subtitles: [{
                text: "TÜRKİYE COVID-19 HASTA TABLOSU"
            }],
            axisY: {
                title: "",
                suffix: "",
                maximum: 2500
            },
            data: [{
                type: "column",
                yValueFormatString: "#",
                indexLabel: "{y}",
                dataPoints: dps
            }]
        };
        return (
            <div>
                <h1>Tarih</h1>

                <DatePicker
                    selected={this.state.reportDate}
                    onChange={this.handleDateChange}
                    name="startDate"
                    dateFormat="dd/MM/yyyy"
                />
                <Select
                    options={cityOptionList} onChange={this.handleSelectChange} value={this.state.selectedCity}>
                </Select>
                <ReactBootStrap.Form>
                    <ReactBootStrap.Form.Check type="checkbox" label="Kümülatif" checked={this.state.isCumulative}
                                               onChange={this.handleCheckBoxChange}/>
                </ReactBootStrap.Form>
                <div>
                </div>
                <CanvasJSChart options={options}
                               onRef={ref => this.chart = ref}
                />
            </div>
        );
    }
}

export default ReportCovid;
