import{_ as b,c as v,o as d,r as H,x as m,t as h,G as z,n as F,R as U,d as y,y as A,p as q,a as e,F as B,A as C,U as W,l as E,e as k,h as J,i as $,q as T,S as K,g as Q,f as N,z as R,j as X}from"./index-CBsz0l9F.js";import{b as Y,a as Z}from"./star_outline-Cw4kId92.js";import{p as ee,a as te,b as se}from"./sample_profile3-DzvHCZTU.js";const ae={},oe={class:"hashtag-badge bodyMedium12px"};function re(s,t){return d(),v("span",oe,[H(s.$slots,"default",{},void 0,!0)])}const ce=b(ae,[["render",re],["__scopeId","data-v-babe9da8"]]),ne={__name:"BtnAgency",props:{color:{type:String,default:"var(--color-primary)"},text:{type:String,required:!0}},emits:["click"],setup(s){const t=s,o=m(()=>{var c;const r=(c=t.color)==null?void 0:c.toLowerCase();return r==="#fff"||r==="#ffffff"||r==="white"});return(r,c)=>(d(),v("button",{class:F(["btn-med-wrapper bodyMedium12px",{"white-bg":o.value}]),style:z({backgroundColor:o.value?"#ffffff":"var(--color-primary)"}),onClick:c[0]||(c[0]=n=>r.$emit("click"))},h(s.text),7))}},D=b(ne,[["__scopeId","data-v-e43549f9"]]),ie={class:"gauge-svg-wrap"},le=["width","height","viewBox"],de=["cx","cy","r","stroke-width"],ue=["cx","cy","r","stroke-width","stroke-dasharray","stroke-dashoffset"],he={class:"score-text"},ve={class:"main-score bodyMedium14px"},me=.83,_e={__name:"CircularGauge",props:{score:{type:Number,required:!0},size:{type:Number,default:80},stroke:{type:Number,default:7},color:{type:String,default:"#111f5c"}},setup(s){U(M=>({"4cb2cdf2":s.color}));const t=s,o=m(()=>Math.round(t.size*me)),r=m(()=>t.size/2),c=m(()=>o.value/2),n=m(()=>(o.value-t.stroke)/2),i=m(()=>2*Math.PI*n.value),a=m(()=>Math.max(0,Math.min(100,t.score/5*100))),p=m(()=>i.value*(1-a.value/100)),_=y(i.value);function f(){setTimeout(()=>{_.value=p.value},80)}A(()=>{_.value=i.value,f()}),q(()=>t.score,()=>{_.value=i.value,f()});const S=m(()=>t.score.toFixed(1));return(M,x)=>(d(),v("div",{class:"circular-gauge",style:z({width:s.size+"px",height:s.size+"px"})},[e("div",ie,[(d(),v("svg",{width:s.size,height:o.value,viewBox:`0 0 ${s.size} ${o.value}`},[e("circle",{class:"bg",cx:r.value,cy:c.value,r:n.value,fill:"none","stroke-width":s.stroke},null,8,de),e("circle",{class:"progress",cx:r.value,cy:c.value,r:n.value,fill:"none","stroke-width":s.stroke,"stroke-dasharray":i.value,"stroke-dashoffset":_.value,style:{transition:"stroke-dashoffset 0.8s cubic-bezier(.4,0,.2,1)"}},null,8,ue)],8,le)),e("div",he,[e("span",ve,h(S.value),1),x[0]||(x[0]=e("span",{class:"sub-score bodyLight12px"},"/ 5",-1))])]),x[1]||(x[1]=e("div",{class:"gauge-caption bodyMedium10px"},"전체 지수",-1))],4))}},fe=b(_e,[["__scopeId","data-v-39a6710a"]]),pe={class:"bar-chart-container"},ge={class:"chart-bars"},ye={class:"label"},be={class:"bar-wrapper"},xe={class:"score bodyLight6px"},we={__name:"BarChart",props:{metricScores:{type:Object,required:!0}},setup(s){const t=s;function o(i){return Math.round(i/100*50)/10}const r=m(()=>!t.metricScores||Object.keys(t.metricScores).length===0?[]:[{label:"매물 신뢰도",width:t.metricScores.listing_accuracy_score,score:o(t.metricScores.listing_accuracy_score)},{label:"비용 투명성",width:t.metricScores.cost_transparency_score,score:o(t.metricScores.cost_transparency_score)},{label:"책임감",width:t.metricScores.accountability_score,score:o(t.metricScores.accountability_score)},{label:"전문성/태도",width:t.metricScores.professionalism_score,score:o(t.metricScores.professionalism_score)}]),c=y([0,0,0,0]);function n(){if(!r.value.length||r.value.length<4){c.value=[0,0,0,0];return}c.value=[0,0,0,0],W(()=>{setTimeout(()=>{c.value=r.value.map(i=>i.width)},60)})}return A(n),q(()=>r.value.map(i=>i.width),n,{immediate:!0}),(i,a)=>(d(),v("div",pe,[a[0]||(a[0]=e("div",{class:"chart-side"},null,-1)),e("div",ge,[(d(!0),v(B,null,C(r.value,(p,_)=>(d(),v("div",{key:p.label,class:"chart-item bodyMedium10px"},[e("span",ye,h(p.label),1),e("div",be,[e("div",{class:"bar",style:z({width:c.value[_]+"%"})},null,4)]),e("span",xe,h(p.score.toFixed(1))+" / 5",1)]))),128))])]))}},$e=b(we,[["__scopeId","data-v-4e3364ca"]]),ke={key:0,class:"review-summary"},Se={class:"summary-container row-wrap"},Me={class:"summary-gauge"},Ie={class:"summary-right"},Be={__name:"AgencyReviewSummary",props:{scoreData:{type:Object,default:()=>null}},setup(s){function t(o){return Math.round(o/100*50)/10}return(o,r)=>s.scoreData?(d(),v("section",ke,[e("div",Se,[e("div",Me,[k(fe,{score:t(s.scoreData.finalTrustScore),size:85},null,8,["score"])]),e("div",Ie,[k($e,{"metric-scores":s.scoreData.averageMetricScores},null,8,["metric-scores"])])])])):E("",!0)}},Ce=b(Be,[["__scopeId","data-v-0e930cec"]]),ze={class:"review-item"},Ae={class:"review-header"},Le={class:"reviewer-name bodyMedium12px"},Te={class:"review-date bodyLight10px"},Ne={class:"review-content bodyLight12px"},Re={__name:"ReviewBox",props:{index:{type:Number,required:!0},date:{type:Object,required:!0},content:{type:String,required:!0}},setup(s){const t=s,o=m(()=>t.date?(t.date[1]<10&&(t.date[1]="0"+t.date[1]),t.date[2]<10&&(t.date[2]="0"+t.date[2]),`${t.date[0]}.${t.date[1]}.${t.date[2]}`):"");return(r,c)=>(d(),v("div",ze,[e("div",Ae,[e("span",Le,"익명"+h(s.index),1),e("span",Te,h(o.value),1)]),e("div",Ne,h(s.content),1)]))}},De=b(Re,[["__scopeId","data-v-0b1f14b1"]]),Fe=["title"],qe=["src"],Oe={class:"agency-profile-wrap"},je={class:"profile-row"},Ge=["src"],Ve={class:"profile-info"},Pe={class:"agency-name titleBold16px"},He={class:"hashtags"},Ue={class:"agency-info-table"},We={class:"bodyLight12px"},Ee={class:"bodyLight12px"},Je={class:"bodyLight12px"},Ke={class:"bodyLight12px"},Qe={class:"agency-description"},Xe={class:"desc-content bodyLight12px"},Ye={class:"agency-btn-row"},Ze={key:0,class:"review-list-wrap"},et={key:1,class:"bodyLight12px",style:{"text-align":"center",color:"var(--color-mediumgray)",margin:"24px 0"}},tt={__name:"AgencyDetail",setup(s){const t=[ee,te,se],o=y(t[Math.floor(Math.random()*t.length)]),r=Q(),c=J(),n=c.query.agencyId||c.params.agencyId||"1",i=localStorage.getItem("user-token"),a=y({office_name:"",profileUrl:"",hashtags:[],agent_name:"",license_number:"",address:"",phone:"",description:""}),p=y({finalTrustScore:"",averageMetricScores:{listing_accuracy_score:"",cost_transparency_score:"",professionalism_score:"",accountability_score:""}}),_=y([]),f=y(!1);async function S(){try{const u=await $.get(`/api/agent/${i}/isFavorite/${n}`);f.value=u.data}catch{f.value=!1}}async function M(){try{f.value?(await $.delete(`/api/agent/${i}/favorite/${n}`),f.value=!1):(await $.get(`/api/agent/${i}/favorite/${n}`),f.value=!0)}catch{alert("북마크 처리 중 오류가 발생했습니다.")}}A(async()=>{try{const u=await $.get(`http://54.66.153.95:8080/api/agent/${n}`);if(a.value={...a.value,office_name:u.data.officeName,agent_name:u.data.agentName,license_number:u.data.licenseNumber,address:u.data.address,phone:u.data.phone,description:u.data.description,hastags:u.data.hashtags||[]},!a.value.description||a.value.description.trim()===""){const w=[`🏢 안녕하세요, 젊은 감각을 가진 공인중개사입니다!

    저희는 최신 트렌드와 다양한 주거 니즈를 반영하여, 고객님의 라이프스타일에 꼭 맞는 공간을 제안해드립니다. 정직함과 신뢰를 바탕으로 항상 고객 중심의 중개를 약속합니다.

    ✔ 합리적인 가격, 빠른 매물 업데이트, 세심한 매물 관리를 최우선으로 합니다.
    ✔ 친근한 상담과 신속한 계약으로 믿고 맡기실 수 있습니다.

    ✨ 언제든 문의주시면 성심껏 도와드리겠습니다.
    여러분의 새로운 시작을 응원합니다! 😊`,`👩‍👧 안녕하세요, 여러분을 위해 발로 뛰는 중개사가 대기하고 있습니다!

    한 분 한 분의 소중한 집을 찾아드리는 것을 최우선으로 생각합니다. 처음부터 끝까지 꼼꼼하게 챙기며, 따뜻한 관심으로 정직한 거래를 실현합니다.

    ✔ 부담 없이 상담부터 시작하세요! 
    ✔ 믿을 수 있는 부동산 거래, 저희 부동산이 책임집니다.

    ✨ 언제든 문의주시면 최선을 다하겠습니다.
    고객님의 행복한 새 출발을 응원합니다! 😊`,`⏱ 안녕하세요, 시간은 금이다!

    빠르고 정확한 매물 소개로 고객님의 소중한 시간을 아껴드립니다. 투명한 정보 제공과 정직한 중개로 언제나 믿고 맡기실 수 있습니다.

    ✔ 신속한 매물 안내, 꼼꼼한 계약 진행
    ✔ 믿음직한 서비스로 스트레스 없는 거래를 약속드립니다.

    ✨ 언제든 문의주세요!
    고객님의 만족을 위해 항상 최선을 다하겠습니다. 😊`,`🤝 공감과 신뢰를 바탕으로 하는 정직한 중개사무소입니다!

    고객 한 분 한 분의 상황에 공감하며, 가장 이득이 되는 거래를 위해 늘 고민합니다. 신뢰와 약속을 최우선 가치로 삼아, 진심을 담아 상담해드립니다.

    ✔ 꼼꼼한 매물 안내와 고객 맞춤형 중개 서비스
    ✔ 정직하고 투명한 거래를 약속합니다.

    ✨ 언제든 편하게 연락 주세요.
    여러분의 든든한 부동산 파트너가 되겠습니다! 😊`,`🔎 광진구 제일의 부동산입니다!

    고객님이 원하는 집을 찾을 때까지 함께 고민하고 끝까지 책임집니다. 합리적인 가격, 세심한 매물 관리, 친절한 상담으로 신뢰받는 부동산이 되겠습니다.

    ✔ 다양한 매물 라인업, 꼼꼼한 비교 안내
    ✔ 친근하고 성실한 상담 서비스 제공

    ✨ 언제든 문의주시면 최선을 다하겠습니다.
    여러분의 행복한 내 집 마련을 응원합니다! 😊`,`🏡 내 집을 구하는 마음으로 정성을 다하는 공인중개사입니다!

    고객 한 분 한 분의 입장에서 최적의 매물을 추천드리며, 내 가족이 머물 집처럼 신중하게 중개합니다. 진심을 담은 서비스로 최고의 만족을 드리겠습니다.

    ✔ 맞춤형 매물 추천, 투명한 정보 제공
    ✔ 꼼꼼하고 믿을 수 있는 계약 진행

    ✨ 언제든 편하게 문의주세요!
    고객님의 새로운 시작을 진심으로 응원합니다. 😊`,`🎓 부동산학 박사 임원진이 함께하는 전문 중개법인입니다!

    경매·공매 분야에 특화된 전문가들이 실전 경험과 전문 지식으로 고객님께 맞춤 솔루션을 제공합니다. 복잡한 거래도 쉽게 풀어드릴 수 있도록 언제나 최선을 다합니다.

    ✔ 전문적이고 신뢰할 수 있는 매물 안내
    ✔ 상세하고 꼼꼼한 계약 진행 지원

    ✨ 언제든 문의주시면 성심껏 도와드리겠습니다.
    여러분의 성공적인 거래를 응원합니다! 😊`,`📍 24시간 준비되어있는 중개사무소 입니다!

    지역 전문가로서 평일·공휴일 언제든 방문하실 수 있고, 일요일은 휴무입니다. 현장 방문과 꼼꼼한 상담으로 고객님께 꼭 맞는 집을 찾을 수 있도록 함께합니다.

    ✔ 지역별 실시간 매물 안내, 맞춤형 추천
    ✔ 친절하고 신속한 상담 서비스

    ✨ 언제든 편하게 연락 주세요!
    여러분의 든든한 동반자가 되어드리겠습니다. 😊`,`📞 신의와 성실로 상담해드리는 공인중개사입니다!

    고객님의 입장에서 생각하며, 처음부터 끝까지 친절하게 안내합니다. 부담 없이 전화 주시면 항상 최선을 다해 상담해드리겠습니다.

    ✔ 맞춤형 상담, 꼼꼼한 계약 진행
    ✔ 믿을 수 있는 부동산 거래를 약속드립니다.

    ✨ 언제든 문의주세요!
    여러분의 만족을 위해 최선을 다하겠습니다. 😊`],I=Math.floor(Math.random()*w.length);a.value.description=w[I]}if(!a.value.hashtags||a.value.hashtags.length===0){let I=function(j,L=3,G=4){const V=[...j].sort(()=>.5-Math.random()),P=Math.floor(Math.random()*(G-L+1))+L;return V.slice(0,P)};const w=["#오피스텔","#오피스","#전월세","#월세","#전세","#원룸","#투룸","#상가","#빌라","#아파트","#신축","#분양","#매매","#임대","#신뢰감","#친절함","#발품중개","#현장방문","#정직중개","#역세권"];a.value.hashtags=I(w)}const l=await $.get(`http://54.66.153.95:8080/api/agent/trustScore/${n}`);p.value={...p.value,finalTrustScore:l.data.totalTrustScore,averageMetricScores:{listing_accuracy_score:l.data.listingAccuracyScore,cost_transparency_score:l.data.costTransparencyScore,professionalism_score:l.data.professionalismScore,accountability_score:l.data.accountabilityScore}};const g=await $.get(`http://54.66.153.95:8080/api/agent/reviews/${n}`);_.value=g.data,await S()}catch{alert("중개사무소 정보를 불러오지 못했습니다.")}});const x=()=>{r.push(`/agency/${n}/listings`)},O=()=>{r.push(`/agency/${n}/review-write`)};return(u,l)=>(d(),v("div",null,[k(K,{title:a.value.office_name},{action:T(()=>[e("button",{class:"bookmark-btn",onClick:M,"aria-label":"북마크",title:f.value?"북마크 해제":"북마크 등록"},[e("img",{src:f.value?N(Y):N(Z),class:F(["bookmark-icon",{pop:u.heartAnim}]),onAnimationend:l[0]||(l[0]=g=>u.heartAnim=!1),alt:"북마크"},null,42,qe)],8,Fe)]),_:1},8,["title"]),e("div",Oe,[e("div",je,[e("img",{src:o.value,alt:"프로필",class:"profile-img"},null,8,Ge),e("div",Ve,[e("div",Pe,h(a.value.office_name),1),e("div",He,[(d(!0),v(B,null,C(a.value.hashtags,g=>(d(),R(ce,{key:g},{default:T(()=>[X(h(g),1)]),_:2},1024))),128))])])]),e("table",Ue,[e("tbody",null,[e("tr",null,[l[1]||(l[1]=e("td",{class:"bodyMedium12px"},"대표",-1)),e("td",We,h(a.value.agent_name),1)]),e("tr",null,[l[2]||(l[2]=e("td",{class:"bodyMedium12px"},"등록번호",-1)),e("td",Ee,h(a.value.license_number),1)]),e("tr",null,[l[3]||(l[3]=e("td",{class:"bodyMedium12px"},"주소",-1)),e("td",Je,h(a.value.address),1)]),e("tr",null,[l[4]||(l[4]=e("td",{class:"bodyMedium12px"},"연락처",-1)),e("td",Ke,h(a.value.phone),1)])])]),e("div",Qe,[e("div",Xe,h(a.value.description||"등록된 소개글이 없습니다."),1)]),e("div",Ye,[k(D,{text:"보유 매물 보러가기",color:"#fff",onClick:x}),k(D,{text:"방문 후기 작성하기",color:"#fff",onClick:O})]),k(Ce,{"score-data":p.value},null,8,["score-data"]),_.value.length>0?(d(),v("div",Ze,[(d(!0),v(B,null,C(_.value,(g,w)=>(d(),R(De,{key:g.id,index:w+1,date:g.createdAt,content:g.comment},null,8,["index","date","content"]))),128))])):(d(),v("div",et," 아직 작성된 후기가 없습니다. "))])]))}},rt=b(tt,[["__scopeId","data-v-32df2c24"]]);export{rt as default};
