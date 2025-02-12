import {
  APPLICATION_REPORT,
  APPLICATION_REPORT_FIELD,
} from '@/data/25/Application';
import { useLocalStorage } from '@/hooks/localstorage.hook';
import RadioButtonsComponent from '@/components/Button/Radio/RadioButtons.component';
import RadioButtonComponent from '@/components/Button/Radio/RadioButton.component';
import useApplicationPageControll from '@/hooks/useApplicationPageControll.hook';

const ApplicationQuestionReport0Component = () => {
  const [type, setType] = useLocalStorage('type', '');
  const [field1, setField1] = useLocalStorage('field1', '');
  const [field2, setField2] = useLocalStorage('field2', '');
  const { goNextPage } = useApplicationPageControll();

  const data = APPLICATION_REPORT[0];

  return (
    <div className="flex">
      <div className="flex w-[30rem]">
        <div className="text-xl font-semibold ">1. </div>
        <div className="pl-4">
          <div className="text-xl font-semibold pb-4">{data.title} *</div>
          <div>{data.subtitle}</div>
        </div>
      </div>
      <div>
        <div className="flex-1 grid grid-cols-2 gap-2 w-[30rem] font-semibold">
          <RadioButtonsComponent
            groupName="area"
            radioButtons={[
              { title: '개발자', value: 'develop' },
              { title: '기획자', value: 'pm' },
              { title: '디자이너', value: 'design' },
            ]}
            radioSelectedStore={[type, (v) => setType(v)]}
          />
        </div>
        {type ? (
          <>
            <div className="py-8">1 순위</div>
            <div className="grid grid-cols-3 gap-2 w-[30rem] font-semibold">
              <RadioButtonsComponent
                groupName="field1"
                disabledValue={field2}
                radioButtons={APPLICATION_REPORT_FIELD.map((field) => {
                  return { title: field, value: field };
                })}
                radioSelectedStore={[field1, (v) => setField1(v)]}
              />
            </div>
            {field1 ? (
              <>
                <div className="py-8">2 순위</div>
                <div className="grid grid-cols-3 gap-2 w-[30rem] font-semibold">
                  <RadioButtonsComponent
                    groupName="field2"
                    disabledValue={field1}
                    radioButtons={APPLICATION_REPORT_FIELD.map((field) => {
                      return { title: field, value: field };
                    })}
                    onClick={goNextPage}
                    radioSelectedStore={[field2, (v) => setField2(v)]}
                  />
                  <div className="col-span-3">
                    <RadioButtonComponent
                      checked={field2 === 'none'}
                      name="field2"
                      onChange={() => {
                        setField2('none');
                      }}
                      title="선택없음"
                      value="none"
                      onClick={goNextPage}
                    />
                  </div>
                </div>
              </>
            ) : (
              ''
            )}
          </>
        ) : (
          ''
        )}
      </div>
    </div>
  );
};

export default ApplicationQuestionReport0Component;
