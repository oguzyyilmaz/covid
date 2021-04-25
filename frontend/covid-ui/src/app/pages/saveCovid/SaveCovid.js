import React, {Component} from 'react';
import * as ReactBootStrap from 'react-bootstrap';
import CovidDataService from '../../data/covid-services/CovidDataService'

export default class SaveCovid extends Component {
    constructor(props) {
        super(props);
        this.onChangeCovidNewsText = this.onChangeCovidNewsText.bind(this);
        this.saveCovidNews = this.saveCovidNews.bind(this);
        this.state = {
            covidNews: ""
        };
    }

    onChangeCovidNewsText(e) {
        this.setState({
            covidNews: e.target.value
        });
    }

    saveCovidNews() {
        var data = {
            covidNews: this.state.covidNews,
        };

        CovidDataService.create(data)

    }

    render() {
        return (
            <ReactBootStrap.Form>
                <ReactBootStrap.Form.Group controlId="exampleForm.ControlTextarea1">
                    <ReactBootStrap.Form.Label>Covid-19 Haberi Ekle</ReactBootStrap.Form.Label>
                    <ReactBootStrap.Form.Control
                        as="textarea"
                        rows={3}
                        value={this.state.covidNews}
                        onChange={this.onChangeCovidNewsText}
                    />
                    <ReactBootStrap.Button
                        variant="primary"
                        type="submit"
                        id="news"
                        onClick={this.saveCovidNews}>
                        Kaydet
                    </ReactBootStrap.Button>
                </ReactBootStrap.Form.Group>
            </ReactBootStrap.Form>
        )
    }
}

