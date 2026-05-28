var base = base || '';
var per = '1.0.1';
require.config({
  baseUrl: base + '/static/js/',
  paths: {
    'watermark':'plus/watermark.js?v=a246d',
    'pForm' : 'pvue/form.js?v=a3147',
    'pTabs' : 'pvue/tabs.js?v=833dd',
    'pCreate' : 'pvue/create.js?v=ae13d',
    'pTools' : 'pvue/tools.js?v=c6550',
    'plus' : 'plus/plus.js?v=a870f',
    'gojs':'lib/go',
    'uploadImages':'plus/uploadImages.js?v=b1ba0',
    'vue':"lib/vue.2.7.8.min.js?v=d9eec",
    'export' : 'plus/export.js?v=c67fa',

  },
  shim:{
    'uploadImages':['WebUploader','jquery','pub','css!plus/uploadImages'],
  }
});
