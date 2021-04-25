import * as ReactBootStrap from 'react-bootstrap';

const Navbar = () => {
    return (
        <ReactBootStrap.Navbar bg="dark" variant="dark" expand="lg">
            {/*<ReactBootStrap.Navbar.Brand href="#home">Ana Sayfa</ReactBootStrap.Navbar.Brand>*/}
            <ReactBootStrap.Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <ReactBootStrap.Navbar.Collapse id="basic-navbar-nav">
                <ReactBootStrap.Nav className="mr-auto">
                    <ReactBootStrap.Nav.Link href="/">Ana Sayfa</ReactBootStrap.Nav.Link>
                    <ReactBootStrap.Nav.Link href="/SaveCovid">Covid-19 HABER EKLE</ReactBootStrap.Nav.Link>
                    <ReactBootStrap.Nav.Link href="/ReportCovid">Covid-19 TAKİP</ReactBootStrap.Nav.Link>
                </ReactBootStrap.Nav>
            </ReactBootStrap.Navbar.Collapse>
        </ReactBootStrap.Navbar>
    );
};

export default Navbar;
