import './App.css';
import SaveCovid from './app/pages/saveCovid/SaveCovid';
import ReportCovid from './app/pages/reportCovid/ReportCovid';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Navbar from "./Navbar";

function App() {
    return (
        <Router>
            <div className="App">
                <Navbar/>
                <h1>Covid-19 TAKİP PANELİ</h1>
            </div>
            <Switch>
                <Route exact path="/saveCovid">
                    <SaveCovid/>
                </Route>
                <Route exact path="/reportCovid">
                    <ReportCovid/>
                </Route>
            </Switch>
        </Router>
    );
}

export default App;
