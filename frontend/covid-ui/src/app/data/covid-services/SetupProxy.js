import * as express from 'express';
import {createProxyMiddleware} from 'http-proxy-middleware';

const app = express();

app.use('/saveCovidNews',
        createProxyMiddleware({
            target: 'http://www.localhost:8080',
            changeOrigin: true,
            secure: false }));
app.listen(3000);
